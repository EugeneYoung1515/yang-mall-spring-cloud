package com.ywcjxf.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rabbitmq.client.Channel;
import com.ywcjxf.mall.dao.*;
import com.ywcjxf.mall.pojo.*;
import com.ywcjxf.mall.service.quartz.service.QuartzScheduleService;
import com.ywcjxf.mall.service.util.IdUtil;
import com.ywcjxf.mall.service.util.RedisLock;
import com.ywcjxf.mall.service.util.RedisReentrantLock;
import com.ywcjxf.mall.service.util.RetryUtil;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.serviceinterface.dto.SimpleCartDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.DefaultCharset.UTF_8;
import static com.ywcjxf.mall.service.constant.OrderAndPayConstants.*;


@Service
public class OrderServiceImpl implements OrderService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    private RabbitTemplate rabbitTemplate;
    private RedisLock redisLock;
    private RedisTemplate<String,String> redisTemplate;
    private SpecMapper specMapper;

    private OrderServiceImpl orderService;

    private OrderItemSpecMapper orderItemSpecMapper;
    private OrderMapper orderMapper;
    private LogisticsMapper logisticsMapper;

    private ObjectMapper objectMapper;

    private ItemMapper itemMapper;

    private ChinaMapper chinaMapper;

    private RedisReentrantLock redisReentrantLock;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private QuartzScheduleService quartzScheduleService;

    //两个数据类
    //PairDto<A,B,C>
    //SimpleCartDto

    //几个不一致的时间
    //数据库订单表相关记录 createDate
    //redis中的订单 设置的时间
    //用于返库存 到达延时队列的时间

    //redis 校验库存 预先减库存 为返库存做准备
    //数据库实际减库存 只是防止丢失更新 没有防止超卖

    /*
    redis中没有订单---------------------------》尝试redis中减库存，有的规格库存不够的话，其他已经减的就返回库存----》数据库减库存，延时任务返redis和数据库库存
redis中有订单--》redis返库存，数据库返回库存---》

decrInventoryAndSend---》decrInventoryRedis---》decrInventoryRedis、decrInventoryAndSend2-----》receiveAndDecrInventory、receiveAndIncrInventory

decrInventoryAndSend2用来数据库减库存和返回库存

receiveAndIncrInventory用来延时任务返redis和数据库库存

     */

    @Override
    public Map<Integer,String> decrInventoryAndSend(Map<Integer, Integer> map, User user) {//没有检验map数量
        RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(LOCK_USER_ORDER+user.getUserId());
        if(lockEntity.isAcquire()){
            try {

                Map<Integer, String> quantityInfo = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);//用于放库存不够的提示信息
                Map<Integer,Integer> attemptToIncrInventory = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);//遇到一个库存不够的 就要用这个把其他的返库存
                //库存都足够 这个就相当于这个方法传进来的map

                    HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                    Map<Integer, Integer> orderRedisMap = hashOperations.entries(ORDER + user.getUserId());

                    if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                        decrInventoryRedis(map, hashOperations, user, quantityInfo, attemptToIncrInventory);
                    } else {

                        //redis返库存
                        redisTemplate.executePipelined(new RedisCallback<Object>() {
                            @Override
                            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                                orderRedisMap.forEach((k, v) -> {
                                    redisConnection.hIncrBy(INVENTORY.getBytes(Charset.forName(UTF_8)), (k + "").getBytes(Charset.forName(UTF_8)), v);
                                });

                                return null;
                            }
                        });

                        redisTemplate.delete(ORDER + user.getUserId());
                        redisTemplate.delete(ORDER_USER_IDENTIFIER + user.getUserId());
                        redisTemplate.delete(ORDER_USER_ORDER_ID+user.getUserId());

                        //上面是redis中 返回库存 这里是数据库里返回库存
                        Map<Integer, Integer> orderRedisMapReverse = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
                        orderRedisMap.forEach((k, v) -> {
                            orderRedisMapReverse.put(k, -v);
                        });
                        decrInventoryAndSend2(orderRedisMapReverse, user, false,null);//数据库返库存

                        decrInventoryRedis(map, hashOperations, user, quantityInfo, attemptToIncrInventory);
                }

                return quantityInfo;

            }finally {
                lockEntity.releaseLock();
            }
        }//没有处理抢不到锁操作
        logger.error("can't get lock");
        return null;
    }

    private void decrInventoryRedis(Map<Integer,Integer> userMap,HashOperations<String,Integer,Integer> hashOperations,User user,
                                    Map<Integer,String> quantityInfo,Map<Integer,Integer> map){

        userMap.forEach((k, v) -> {//尝试减redis中库存
            decrInventoryRedis(hashOperations, k, v, quantityInfo,map);
        });

        if(!quantityInfo.isEmpty()){//遇到redis库存不足的就返库存

            redisTemplate.executePipelined(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    map.forEach((k, v) -> {
                        redisConnection.hIncrBy(INVENTORY.getBytes(Charset.forName(UTF_8)), (k + "").getBytes(Charset.forName(UTF_8)), v);
                    });

                    return null;
                }
            });

        }else{

            hashOperations.putAll(ORDER+user.getUserId(),map);


            Long orderId = IdUtil.nextId();

            decrInventoryAndSend2(map,user,true,orderId);//数据库减库存

            redisTemplate.expire(ORDER+user.getUserId(),TEN_MIN,TimeUnit.MINUTES);

            String identifier = UUID.randomUUID().toString();
            //redisTemplate.opsForValue().set("order:user:identifier:"+user.getUserId(),identifier);
            //redisTemplate.opsForValue().set("order:user:orderId:"+user.getUserId(),orderId+"");

            redisTemplate.opsForValue().set(ORDER_USER_IDENTIFIER+user.getUserId(),identifier,TWENTY_MIN,TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(ORDER_USER_ORDER_ID+user.getUserId(),orderId+"",TWENTY_MIN,TimeUnit.MINUTES);

            SimpleCartDto simpleCartDto = new SimpleCartDto();
            simpleCartDto.setUserId(user.getUserId());
            simpleCartDto.setIdentifier(identifier);
            simpleCartDto.setMap(map);

            simpleCartDto.setOrderId(orderId);

            CorrelationData correlationData = new CorrelationData(identifier);
            rabbitTemplate.convertAndSend(RABBITMQ_INCR_INVENTORY, simpleCartDto, correlationData);//延时任务返库存

            logger.debug("schedule");
            quartzScheduleService.schedule(simpleCartDto,TEN_MIN);

            redisTemplate.delete(ORDER_LIST + user.getUserId());
            redisTemplate.delete(ORDER_STAT+user.getUserId());
            //加后面这两个是为了让 通过redis中的标志判断异步操作的结果

            //redis中的订单和redis中的"order:list:" + user.getUserId()（"order:stat:"+user.getUserId()）
            //可能不是同一个同一个订单的

        }

    }


    //redis中几个标志
    //"order:" + user.getUserId()
    //"order:user:identifier:"+user.getUserId()
    //订单是否过期 订单是否被覆盖了

    //"order:list:" + user.getUserId()
    //"order:stat:"+user.getUserId()
    //异步操作是否成功？

    //减redis库存
    private void decrInventoryRedis(HashOperations<String,Integer,Integer> hashOperations,Integer k,Integer v,
                                    Map<Integer,String> quantityInfo,Map<Integer,Integer> map){
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout(LOCK_SPEC+k);
        if(pairDto.getFirst()) {
            Integer inv = hashOperations.get(INVENTORY, k);
            if (v <= inv) {
                hashOperations.increment(INVENTORY, k, -v);
                map.put(k,v);
                redisLock.releaseLock(LOCK_SPEC+k,pairDto.getSecond());
            }else{
                quantityInfo.put(k,"库存不足，最大库存为"+inv);
                redisLock.releaseLock(LOCK_SPEC+k,pairDto.getSecond());
            }
        }
    }

    //发 用于减数据库库存/返回库存
    private void decrInventoryAndSend2(Map<Integer,Integer> map,User user,Boolean isOrder,Long orderId){
        SimpleCartDto cartDto = new SimpleCartDto();
        cartDto.setMap(map);
        cartDto.setUserId(user.getUserId());

        cartDto.setUser(user);
        cartDto.setOrder(isOrder);
        cartDto.setCreateDate(new Date());

        cartDto.setOrderId(orderId);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RABBITMQ_ORDER, cartDto, correlationData);
    }

    @RabbitListener(queues = RABBITMQ_ORDER)//收 用于减数据库库存
    public void receiveAndDecrInventory(SimpleCartDto cartDto) {
        defaultRetry(()->orderService.decrInventoryDb(cartDto));
    }


    //收 用于加redis库存 和数据库库存
    @RabbitListener(queues = RABBITMQ_DEAD_QUEUE_INCR_INVENTORY)
    public void receiveAndIncrInventory(SimpleCartDto cartDto){
        logger.info("run");
        receiveAndIncrInventory(cartDto,EMPTY_STRING_PREFIX);
    }

    public void receiveAndIncrInventory(SimpleCartDto cartDto,String pay){//10min返回库存 30min返回库存都是用这个
        if(pay==null){
            pay=EMPTY_STRING_PREFIX;
        }

        final String finalPay = pay;

        defaultRetry(() -> {
            String identifier = cartDto.getIdentifier();

            RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(finalPay+LOCK_USER_ORDER+cartDto.getUserId());
            if(lockEntity.isAcquire()){
                try{

                    String redisIdentifier = redisTemplate.opsForValue().get(finalPay+ORDER_USER_IDENTIFIER+cartDto.getUserId());
                    if(redisIdentifier!=null&&redisIdentifier.equals(identifier)) {
                        cartDto.getMap().forEach((k, v) -> {

                            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                            hashOperations.increment(INVENTORY, k, v);

                            //System.out.println(k + " " + v);
                            logger.info(k + " " + v);

                            //specMapper.updateInventory(k,v);

                            Spec spec = new Spec();
                            spec.setSpecId(k);
                            spec.setNowInventory(v);

                            specMapper.updateInventory(spec);

                        });

                        redisTemplate.delete(finalPay+ORDER_USER_IDENTIFIER + cartDto.getUserId());
                        redisTemplate.delete(finalPay+ORDER + cartDto.getUserId());//这里有竞态条件 过了十分钟要过期时 运行到这句 有新订单进来 会触发重复返库存//
                        redisTemplate.delete(finalPay+ORDER_USER_ORDER_ID+cartDto.getUserId());
                    }

                }finally {
                    lockEntity.releaseLock();
                }
            }
        });

    }

    @Transactional//减数据库库存 生成订单
    public void decrInventoryDb(SimpleCartDto cartDto){

        if(cartDto.getOrder()){

            Long orderId = cartDto.getOrderId();

            //Long orderId = IdUtil.nextId();
            //Date createDate = new Date();
            Date createDate = cartDto.getCreateDate();

            Order order = Order.newBuilder().orderId(orderId).createDate(createDate).userId(cartDto.getUser().getUserId())
                    .userName(cartDto.getUser().getUserName()).statusCode(10).statusName("已下单").build();

            //需要有一个关于运费的策略

            BigDecimal[] itemTotal = {new BigDecimal(0)};

            List<OrderItemSpec> orderItemSpecList = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);

            cartDto.getMap().forEach((k, v) -> {

                Spec spec = specMapper.selectForUpdateById(k);

                spec.setNowInventory(spec.getNowInventory() - v);
                //spec.setSales(spec.getSales()+cartDto.getQuantity());
                specMapper.updateByPrimaryKeySelective(spec);

                itemTotal[0] = itemTotal[0].add(spec.getRepresentPrice().multiply(new BigDecimal(v)));

                //需要有一个关于运费的策略

                OrderItemSpec orderItemSpec = new OrderItemSpec(k,orderId,spec.getSpecTitle(),spec.getSpecName(),spec.getSalePrice(),
                        spec.getRepresentPrice(),v,spec.getItemId());
                orderItemSpecList.add(orderItemSpec);

            });

            orderItemSpecMapper.insertBatch(orderItemSpecList);

            order.setItemTotal(itemTotal[0]);
            BigDecimal yunfeiCost = new BigDecimal(10);
            order.setYunfeiCost(yunfeiCost);
            order.setPayTotal(itemTotal[0].add(yunfeiCost));
            //orderMapper.insertSelective(order);
            orderMapper.insertSelectiveOrUpdateSelective(order);

            Logistics logistics = new Logistics();
            logistics.setCreateDate(createDate);
            logistics.setOrderId(orderId);
            logistics.setYunfeiCost(yunfeiCost.toString());
            logistics.setUserId(cartDto.getUser().getUserId());
            logisticsMapper.insertSelectiveOrUpdateSelective(logistics);


            try {//这两个的目的 是为了重定向到订单页时为了不重新计算
                redisTemplate.opsForValue().set(ORDER_LIST + cartDto.getUserId(), objectMapper.writeValueAsString(orderItemSpecList),TEN_MIN,TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(ORDER_STAT+cartDto.getUserId(),objectMapper.writeValueAsString(order),TEN_MIN,TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(ORDER_STAT_ORDER_ID+cartDto.getUserId(),orderId+"",TEN_MIN,TimeUnit.MINUTES);
            }catch (JsonProcessingException ex){
                //ex.printStackTrace();

                logger.error("objectMapper: "+"_"+ex.getMessage(),ex);
            }


        }else {

            cartDto.getMap().forEach((k, v) -> {

                Spec spec = specMapper.selectForUpdateById(k);
                spec.setNowInventory(spec.getNowInventory() - v);
                //spec.setSales(spec.getSales()+cartDto.getQuantity());
                specMapper.updateByPrimaryKeySelective(spec);

            });
        }

    }

    public void defaultRetry(RetryUtil.Handler handler){
        Map<Object,Boolean> map = new HashMap<>(16);
        try {
            RetryUtil.retry(0, 1000, map,
                    () -> {//Handler
                        //核心代码在这一块

                        handler.run();

                    },
                    (ex) -> {//Recoverer
                        throw new AmqpRejectAndDontRequeueException("Recorver");
                    });
        }catch (Exception ex){
            if(ex instanceof AmqpRejectAndDontRequeueException){
                throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }else{
                //System.out.println(ex);//这里没有替换成日志
                //throw new RuntimeException(ex.getMessage(),ex.getCause());

                throw new RuntimeException(ex);
               // throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }

        }
    }

    public void test(){
        /*
        Map<Integer,Integer> map = new HashMap<>(16);
        User user = new User();
        user.setUserId(2);

        for(int i = 1;i<3;i++) {
            int ram = new Random().nextInt(3) + 9;
            map.put(i, ram * 10);
            //System.out.println(ram * 10);
        }
        decrInventoryAndSend(map,user);
        */
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(!b){
            //System.out.println("correlationDataId "+correlationData.getId());
            //System.out.println("cause "+s);
            logger.info("correlationDataId "+correlationData.getId());
            logger.info("cause "+s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        //System.out.println("returnedMessage");
        //System.out.println(message);

        logger.info("returnedMessage");
        logger.info(message.toString());
    }
    //这个能起效 上面的 convertAndSend 发到错误的routingKey 这里就会触发

    @PostConstruct
    public void inventoryToRedis(){
        //System.out.println("PostConstruct");
        logger.info("PostConstruct");

        int resultNum = specMapper.selectCount();
        int total = resultNum/20;
        if(resultNum%20 != 0){
            total++;
        }

        for(int i = 1;i<=total;i++) {
            PageHelper.startPage(i, 20, false);
            Page<Spec> list = specMapper.selectAllOnlyIdAndInventory();//这里要不要分页取 一页20个
            //要不要再用上线程池

            Map<Integer, Integer> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
            for (Spec spec : list) {
                map.put(spec.getSpecId(), spec.getNowInventory());
            }

            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll(INVENTORY, map);
        }
    }


    /*
    购物车页提交
异步到数据库生成订单记录和物流记录
或者说
排队到数据库生成订单记录和物流记录

然后从定向到订单页
又要立刻获得订单记录和物流记录

1.自己是
先判断redis中有没有
没有的话
	就重新查数据库 生成订单记录和物流记录

这样会有不一致存在
价格变化
以及要是数据库那里生成失败

2.前端轮训
重定向或者get请求订单页
订单页中表单先显示空白
通过ajax轮训


     */

    //自己又重新计算了 因为异步到数据库 不能立刻获得结果
    public PairDto<Order,List<OrderItemSpec>,String> getOrder(User user){
        HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
        Map<Integer,Integer> map = hashOperations.entries(ORDER+user.getUserId());
        if(map!=null && !map.isEmpty()){
            String orderListJson = redisTemplate.opsForValue().get(ORDER_LIST+user.getUserId());
            String orderStatJson = redisTemplate.opsForValue().get(ORDER_STAT+user.getUserId());
            String orderId = redisTemplate.opsForValue().get(ORDER_USER_ORDER_ID+user.getUserId());
            String orderId1 = redisTemplate.opsForValue().get(ORDER_STAT_ORDER_ID+user.getUserId());

            List<OrderItemSpec> orderItemSpecList = null;
            Order order = null;

            List<Integer> idList = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);

            if(orderListJson!=null && orderStatJson!=null&&orderId1!=null&&orderId1.equals(orderId)){//这样也不行 redis中的旧值存在的话 这个这个判断也通过 下面也有一处这样
                //为了让这个判断行 异步操作前 就删除旧值

                try {
                    orderItemSpecList = objectMapper.readValue(orderListJson, new TypeReference<ArrayList<OrderItemSpec>>() {});
                    order = objectMapper.readValue(orderStatJson,Order.class);

                    for (OrderItemSpec orderItemSpec:orderItemSpecList){
                        idList.add(orderItemSpec.getItemId());
                    }

                }catch (IOException ex){
                    //ex.printStackTrace();

                    logger.error("objectMapper"+"_"+ex.getMessage(),ex);
                }

            }else{

                BigDecimal itemTotal = new BigDecimal(0);
                 orderItemSpecList = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
                List<Spec> specs = specMapper.selectAllSpecById(new ArrayList<>(map.keySet()));
                for (Spec spec : specs) {
                    Integer quantity = map.get(spec.getSpecId());
                    BigDecimal eachSpecTotal = spec.getRepresentPrice().multiply(new BigDecimal(quantity));
                    OrderItemSpec orderItemSpec = new OrderItemSpec();
                    orderItemSpec.setSpecId(spec.getSpecId());
                    orderItemSpec.setQuantity(quantity);
                    orderItemSpec.setRepresentPrice(spec.getRepresentPrice());
                    orderItemSpec.setEachSpecTotal(eachSpecTotal.toString());
                    orderItemSpec.setItemId(spec.getItemId());
                    orderItemSpec.setSpecTitle(spec.getSpecTitle());
                    orderItemSpec.setYunfeiCost(spec.getYunfeiCost());
                    orderItemSpecList.add(orderItemSpec);

                    idList.add(spec.getItemId());

                    itemTotal = itemTotal.add(eachSpecTotal);
                }

                order = new Order();
                order.setItemTotal(itemTotal);
                BigDecimal yunfeiCost = new BigDecimal(10);
                order.setYunfeiCost(yunfeiCost);
                order.setPayTotal(itemTotal.add(yunfeiCost));
            }

            List<Item> items = itemMapper.selectAllItemById(idList);
            Map<Integer, Item> map1 = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
            for (Item item : items) {
                map1.put(item.getItemId(), item);
            }

            for (OrderItemSpec orderItemSpec : orderItemSpecList) {
                 orderItemSpec.setImageUrl(map1.get(orderItemSpec.getItemId()).getImageUrl());
            }

            PairDto<Order,List<OrderItemSpec>,String> pairDto = new PairDto<>();
            pairDto.setFirst(order);
            pairDto.setSecond(orderItemSpecList);

            String identifier = redisTemplate.opsForValue().get(ORDER_USER_IDENTIFIER+user.getUserId());
            pairDto.setThird(identifier+"@@@"+orderId);

            return pairDto;
        }
        //System.out.println("null");
        logger.info("return null");
        return null;
    }

    //还要校验是不是数字
    public Integer validateAddressAndInsert(String trueName,String cityId,
                                            String areaId,String areaInfo,String address,String mobPhone){
        if(cityId.equals("-请选择-")){
            China china = chinaMapper.selectById(Integer.valueOf(areaId));
            if(china==null){
                //丢异常
                throw new RuntimeException(ADDRESS_NOT_EXIST);
            }else{
                Map<String,String> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
                map.put(ADDRESS_PROVINCE,china.getName());
                return setMap(map,trueName,address,areaInfo,mobPhone);
            }
        }
        if(cityId.equals(areaId)){
            China china = chinaMapper.selectById(Integer.valueOf(areaId));
            if(china==null){
                //丢异常
                throw new RuntimeException(ADDRESS_NOT_EXIST);
            }else{
                China china1 = chinaMapper.selectById(china.getParentId());
                Map<String,String> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
                map.put(ADDRESS_PROVINCE,china1.getName());
                map.put(ADDRESS_CITY,china.getName());
                return setMap(map,trueName,address,areaInfo,mobPhone);
            }

        }
        China china = chinaMapper.selectById(Integer.valueOf(cityId));
        China china1 = chinaMapper.selectById(Integer.valueOf(areaId));
        if(china==null||china1==null){
            //丢异常
            throw new RuntimeException(ADDRESS_NOT_EXIST);
        }else{
            China china2 = chinaMapper.selectById(china.getParentId());
            Map<String,String> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
            map.put(ADDRESS_PROVINCE,china2.getName());
            map.put(ADDRESS_CITY,china.getName());
            map.put(ADDRESS_DISTRICT,china1.getName());
            return setMap(map,trueName,address,areaInfo,mobPhone);
        }
    }

    private Integer setMap(Map<String,String> map,String trueName,String address,String areaInfo,String mobPhone){
        map.put(ADDRESS_NAME,trueName);
        map.put(ADDRESS_ADDRESS,address);
        map.put(ADDRESS_AREA_INFO,areaInfo);//相当于概括
        map.put(ADDRESS_PHONE_NUMBER,mobPhone+"");
        Long id = redisTemplate.opsForValue().increment(TEMP_LOGISTICS_ID,1);
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(TEMP_LOGISTICS+id,map);
        redisTemplate.expire(TEMP_LOGISTICS+id,TEN_MIN,TimeUnit.MINUTES);
        return id.intValue();
    }


    public void insert(List<List<String>> lists,Integer id){
        List<China> chinaList = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        for (List<String> list:lists){
            China china = new China();
            china.setId(Integer.valueOf(list.get(0)));
            china.setName(list.get(1));
            china.setParentId(id);
            chinaList.add(china);
        }

        chinaMapper.insertBatch(chinaList);
    }

    //补全订单的信息
    //如果订单已经过期的话 先重新减库存 生成订单
    //再补全订单的信息

    //这个方法重新运行 这个方法之前减的库存 不会重新返库存
    //但是三十分钟后 只会返回一次库存
    public Map<Integer,String> submitOrder(Map<Integer,Integer> map,User user,Integer addressId,String token,String orderId){//要不要在订单页提交的那个form里再埋一个订单号？
        Map<Integer,Integer> map1 = null;

        RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(LOCK_USER_ORDER+user.getUserId());
        if(lockEntity.isAcquire()){
            try{

                String identifier = redisTemplate.opsForValue().get(ORDER_USER_IDENTIFIER + user.getUserId());
                if (identifier == null || !identifier.equals(token)) {
                    Map<Integer, String> quantityInfo = decrInventoryAndSend(map, user);
                    //System.out.println("qaz");
                    logger.info("submit as new order");

                    //注意如果是订单过期了 重新生成订单 下面的 Logistics logistics = logisticsMapper.selectByUserIdOrderByIdLimitOne(user.getUserId());
                    //可能会取到错的
                    //因为 生成订单 是通过消息队列异步发到数据库的

                    if (quantityInfo != null && !quantityInfo.isEmpty()) {
                        return quantityInfo;
                    }

                    while (true){//循环 等到数据库操作完成
                        if (redisTemplate.hasKey(ORDER_LIST + user.getUserId())) {//这样也不行 redis中的旧值存在的话 这个这个判断也通过
                            //为了让这个判断行 异步操作前 就删除旧值
                            break;
                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();//没有替换成日志
                        }
                    }

                    //9月28日思考 想不起来这个循环的作用了 想起来
                    //就是等数据库操作完成 上面已经填写了一些字段 下面还有数据库操作要填写其他字段

                    //但还是有一个问题
                    //这里等的数据库操作完成 可能等的是 之前的订单异步后的数据库操作完成 不是这里提交的订单
                    //补救措施（正确措施）在下面写了 拿到订单id

                    //这个循环删掉 行吗？

                }

                HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
                //System.out.println(map);
                logger.info("compare: parameterMap: "+map);
                map1 = hashOperations.entries(ORDER+user.getUserId());
                //System.out.println(map1);
                logger.info("compare: redisMap: "+map1);
                //System.out.println(orderId);
                logger.info("compare: parameterOrderId: "+orderId);
                orderId = redisTemplate.opsForValue().get(ORDER_USER_ORDER_ID+ user.getUserId());
                //System.out.println(orderId);
                logger.info("compare: redisOrderId: "+orderId);

                redisTemplate.delete(ORDER_USER_IDENTIFIER + user.getUserId());
                redisTemplate.delete(ORDER + user.getUserId());
                redisTemplate.delete(ORDER_USER_ORDER_ID+ user.getUserId());

                //redisTemplate.rename("order:stat:"+user.getUserId(),"pay:order:stat:"+user.getUserId());

            }finally {
                lockEntity.releaseLock();
            }

        }

        decrInventoryAndSendForPay(map1,user,orderId);

        HashOperations<String,String,String> stringHashOperations = redisTemplate.opsForHash();
        Map<String,String> tempLogistics = stringHashOperations.entries(TEMP_LOGISTICS+addressId);


        /*
        qaz
{1=4, 2=4}
{}
1299214317387808
null
打印异常信息：
java.lang.NumberFormatException: null
	at java.lang.Long.parseLong(Long.java:552)
	at java.lang.Long.parseLong(Long.java:631)
	at com.ywcjxf.mall.service.OrderServiceImpl.submitOrder(OrderServiceImpl.java:780)

         */

        Logistics logistics = new Logistics();
        logistics.setOrderId(Long.parseLong(orderId));
        logistics.setProvince(tempLogistics.get(ADDRESS_PROVINCE));
        logistics.setCity(tempLogistics.get(ADDRESS_CITY));
        logistics.setDistrict(tempLogistics.get(ADDRESS_DISTRICT));
        logistics.setName(tempLogistics.get(ADDRESS_NAME));
        logistics.setAddress(tempLogistics.get(ADDRESS_ADDRESS));
        logistics.setAddressInfo(tempLogistics.get(ADDRESS_AREA_INFO));
        logistics.setPhoneNumber(tempLogistics.get(ADDRESS_PHONE_NUMBER));
        logisticsMapper.insertSelectiveOrUpdateSelective(logistics);

        redisTemplate.delete(TEMP_LOGISTICS+addressId);

        return null;
    }

    /*
    填完信息，准备支付的订单 在redis中放置（如果redis中已经有，就redis返库存、数据库返回库存）----》延时任务反redis库存和数据库库存
     */

    public void decrInventoryAndSendForPay(Map<Integer, Integer> map, User user,String orderId) {//没有检验map数量

        RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(LOCK_PAY_USER_ORDER+user.getUserId());
        if(lockEntity.isAcquire()){
            try{

                HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                Map<Integer, Integer> orderRedisMap = hashOperations.entries(PAY_ORDER + user.getUserId());

                if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                    decrInventoryRedis(hashOperations, user,map,orderId);
                } else {

                    //redis返回库存
                    redisTemplate.executePipelined(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            orderRedisMap.forEach((k, v) -> {
                                redisConnection.hIncrBy(INVENTORY.getBytes(Charset.forName(UTF_8)), (k + "").getBytes(Charset.forName(UTF_8)), v);
                            });

                            return null;
                        }
                    });

                    redisTemplate.delete(PAY_ORDER + user.getUserId());
                    redisTemplate.delete(PAY_ORDER_USER_IDENTIFIER + user.getUserId());
                    redisTemplate.delete(PAY_ORDER_USER_ORDER_ID + user.getUserId());

                    Map<Integer, Integer> orderRedisMapReverse = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
                    orderRedisMap.forEach((k, v) -> {
                        orderRedisMapReverse.put(k, -v);
                    });
                    decrInventoryAndSend2(orderRedisMapReverse, user, false,null);//数据库返回库存

                    decrInventoryRedis(hashOperations, user,map,orderId);
                }

            }finally {
                lockEntity.releaseLock();
            }
        }

    }

    //准备延时任务的数据 30min返库存
    private void decrInventoryRedis(HashOperations<String,Integer,Integer> hashOperations,User user,Map<Integer,Integer> map,String orderId){

        hashOperations.putAll(PAY_ORDER+user.getUserId(),map);//redis中放要支付的订单

        redisTemplate.expire(PAY_ORDER+user.getUserId(),THIRTY_MIN,TimeUnit.MINUTES);

        Order order = new Order();
        order.setWaitPayDate(new Date());
        order.setOrderId(Long.parseLong(orderId));
        //orderMapper.updateByOrderId(order);
        orderMapper.insertSelectiveOrUpdateSelective(order);

        String identifier = UUID.randomUUID().toString();
        //redisTemplate.opsForValue().set("pay:order:user:identifier:"+user.getUserId(),identifier);
        //redisTemplate.opsForValue().set("pay:order:user:orderId:"+user.getUserId(),orderId);

        redisTemplate.opsForValue().set(PAY_ORDER_USER_IDENTIFIER+user.getUserId(),identifier,SIXTY_MIN,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(PAY_ORDER_USER_ORDER_ID+user.getUserId(),orderId,SIXTY_MIN,TimeUnit.MINUTES);

        SimpleCartDto simpleCartDto = new SimpleCartDto();
        simpleCartDto.setUserId(user.getUserId());
        simpleCartDto.setIdentifier(identifier);
        simpleCartDto.setMap(map);

        CorrelationData correlationData = new CorrelationData(identifier);
        rabbitTemplate.convertAndSend(RABBITMQ_INCR_INVENTORY_30_MIN, simpleCartDto, correlationData);

        quartzScheduleService.schedule(simpleCartDto,THIRTY_MIN);

    }

    @RabbitListener(queues = RABBITMQ_DEAD_QUEUE_INCR_INVENTORY_30_MIN)
    public void receiveAndIncrInventory30min(SimpleCartDto cartDto){
        receiveAndIncrInventory(cartDto,PAY);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderItemSpecMapper(OrderItemSpecMapper orderItemSpecMapper) {
        this.orderItemSpecMapper = orderItemSpecMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Autowired
    public void setChinaMapper(ChinaMapper chinaMapper) {
        this.chinaMapper = chinaMapper;
    }

    @Autowired
    public void setRedisReentrantLock(RedisReentrantLock redisReentrantLock) {
        this.redisReentrantLock = redisReentrantLock;
    }

    @Autowired
    public void setQuartzScheduleService(QuartzScheduleService quartzScheduleService) {
        this.quartzScheduleService = quartzScheduleService;
    }
}
