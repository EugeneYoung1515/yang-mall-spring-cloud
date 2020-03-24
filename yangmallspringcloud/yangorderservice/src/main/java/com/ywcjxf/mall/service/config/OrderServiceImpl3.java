package com.ywcjxf.mall.service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ywcjxf.mall.dao.*;
import com.ywcjxf.mall.pojo.*;
import com.ywcjxf.mall.service.util.IdUtil;
import com.ywcjxf.mall.service.util.RedisLock;
import com.ywcjxf.mall.service.util.RetryUtil;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.serviceinterface.dto.SimpleCartDto;
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


//@Service
public class OrderServiceImpl3 implements OrderService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    private RabbitTemplate rabbitTemplate;
    private RedisLock redisLock;
    private RedisTemplate<String,String> redisTemplate;
    private SpecMapper specMapper;

    private OrderServiceImpl3 orderService;

    private OrderItemSpecMapper orderItemSpecMapper;
    private OrderMapper orderMapper;
    private LogisticsMapper logisticsMapper;

    private ObjectMapper objectMapper;

    private ItemMapper itemMapper;

    private ChinaMapper chinaMapper;

    //两个数据类
    //PairDto<A,B,C>
    //SimpleCartDto

    //几个不一致的时间
    //数据库订单表相关记录 createDate
    //redis中的订单 设置的时间
    //用于返库存 到达延时队列的时间

    //redis 校验库存 预先减库存 为返库存做准备
    //数据库实际减库存 只是防止丢失更新 没有防止超卖

    @Override
    public Map<Integer,String> decrInventoryAndSend(Map<Integer, Integer> map, User user) {//没有检验map数量
        //long start = System.currentTimeMillis();
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("user:decr:order:"+user.getUserId());
        //这里用分布式锁当限流用

        if(pairDto.getFirst()){

            Map<Integer, String> quantityInfo = new HashMap<>(16);//用于放库存不够的提示信息
            Map<Integer,Integer> attemptToIncrInventory = new HashMap<>(16);//遇到一个库存不够的 就要用这个把其他的返库存
            //库存都足够 这个就相当于这个方法传进来的map

            PairDto<Boolean,String,Object> pairDto1 = redisLock.acquireLockWithTimeout("user:incr:order:"+user.getUserId());
            if(pairDto1.getFirst()) {
                HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                Map<Integer, Integer> orderRedisMap = hashOperations.entries("order:" + user.getUserId());

                if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                    redisLock.releaseLock("user:incr:order:"+user.getUserId(),pairDto1.getSecond());
                    decrInventoryRedis(map, hashOperations, user, quantityInfo, attemptToIncrInventory);
                    //释放锁的语句要不要写在这里
                } else {

                    //redis返库存
                    redisTemplate.executePipelined(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            orderRedisMap.forEach((k, v) -> {
                                redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")), (k + "").getBytes(Charset.forName("UTF-8")), v);
                            });

                            return null;
                        }
                    });

                    redisTemplate.delete("order:" + user.getUserId());
                    //System.out.println(redisTemplate.opsForValue().get("order:user:identifier:" + user.getUserId())+"first");
                    redisTemplate.delete("order:user:identifier:" + user.getUserId());
                    redisTemplate.delete("order:user:orderId:"+user.getUserId());

                    redisLock.releaseLock("user:incr:order:"+user.getUserId(),pairDto1.getSecond());

                    //上面是redis中 返回库存 这里是数据库里返回库存
                /*
                orderRedisMap.forEach((k, v) -> {
                    decrInventoryAndSend2(k, -v, user);
                });
                */
                    //要不要换成 发一整个map 给 消费者

                    Map<Integer, Integer> orderRedisMapReverse = new HashMap<>(16);
                    orderRedisMap.forEach((k, v) -> {
                        orderRedisMapReverse.put(k, -v);
                    });
                    decrInventoryAndSend2(orderRedisMapReverse, user, false,null);//数据库返库存

                    decrInventoryRedis(map, hashOperations, user, quantityInfo, attemptToIncrInventory);
                }
            }

            redisLock.releaseLock("user:decr:order:"+user.getUserId(),pairDto.getSecond());

            //System.out.println((System.currentTimeMillis()-start)+"毫秒");

            return quantityInfo;
        }//没有处理抢不到锁操作

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
                        redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")), (k + "").getBytes(Charset.forName("UTF-8")), v);
                    });

                    return null;
                }
            });

        }else{
            hashOperations.putAll("order:"+user.getUserId(),map);

            /*
            map.forEach((k,v)->{
                decrInventoryAndSend2(k,v,user);
            });
            */
            //要不要换成 发一整个map 给 消费者

            Long orderId = IdUtil.nextId();
            decrInventoryAndSend2(map,user,true,orderId);//数据库减库存

            redisTemplate.expire("order:"+user.getUserId(),10,TimeUnit.MINUTES);

            String identifier = UUID.randomUUID().toString();
            //redisTemplate.opsForValue().set("order:user:identifier:"+user.getUserId(),identifier,1,TimeUnit.MINUTES);
            redisTemplate.opsForValue().set("order:user:identifier:"+user.getUserId(),identifier);
            //System.out.println(identifier+"second");

            redisTemplate.opsForValue().set("order:user:orderId:"+user.getUserId(),orderId+"");

            SimpleCartDto simpleCartDto = new SimpleCartDto();
            simpleCartDto.setUserId(user.getUserId());
            simpleCartDto.setIdentifier(identifier);
            simpleCartDto.setMap(map);

            simpleCartDto.setOrderId(orderId);

            CorrelationData correlationData = new CorrelationData(identifier);
            rabbitTemplate.convertAndSend("incrInventory", simpleCartDto, correlationData);//延时任务返库存

            redisTemplate.delete("order:list:" + user.getUserId());
            redisTemplate.delete("order:stat:"+user.getUserId());
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
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("spec:"+k);
        if(pairDto.getFirst()) {
            Integer inv = hashOperations.get("inventory:", k);
            if (v <= inv) {
                hashOperations.increment("inventory:", k, -v);
                map.put(k,v);
                redisLock.releaseLock("spec:"+k,pairDto.getSecond());
            }else{
                quantityInfo.put(k,"库存不足，最大库存为"+inv);
                redisLock.releaseLock("spec:"+k,pairDto.getSecond());
            }
        }
    }

    //发 用于减数据库库存/返回库存
    private void decrInventoryAndSend2(Integer k,Integer v,User user){
        SimpleCartDto cartDto = new SimpleCartDto();
        cartDto.setGoodsId(k);
        cartDto.setQuantity(v);
        cartDto.setUserId(user.getUserId());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("order", cartDto, correlationData);
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
        rabbitTemplate.convertAndSend("order", cartDto, correlationData);
    }

    //@RabbitListener(queues = "order")//收 用于减数据库库存
    public void receiveAndDecrInventory(SimpleCartDto cartDto) {
        defaultRetry(()->orderService.decrInventoryDb(cartDto));
    }

    /*
    //收 用于加redis库存 和数据库库存
    @RabbitListener(queues = "deadQueueIncrInventory")
    public void receiveAndIncrInventory(SimpleCartDto cartDto){
        defaultRetry(()->{
            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            hashOperations.increment("inventory:",cartDto.getGoodsId(),cartDto.getQuantity());

            specMapper.updateInventory(cartDto.getQuantity(),cartDto.getGoodsId());
        });
    }
    */

    //收 用于加redis库存 和数据库库存
    //@RabbitListener(queues = "deadQueueIncrInventory")
    public void receiveAndIncrInventory(SimpleCartDto cartDto){
        receiveAndIncrInventory(cartDto,"");
    }

    private void receiveAndIncrInventory(SimpleCartDto cartDto,String pay){//10min返回库存 30min返回库存都是用这个
        if(pay==null){
            pay="";
        }

        final String finalPay = pay;

        defaultRetry(() -> {
            String identifier = cartDto.getIdentifier();

            PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout(finalPay+"user:incr:order:"+cartDto.getUserId());
            if(pairDto.getFirst()){
                String redisIdentifier = redisTemplate.opsForValue().get(finalPay+"order:user:identifier:"+cartDto.getUserId());
                if(redisIdentifier!=null&&redisIdentifier.equals(identifier)) {
                    cartDto.getMap().forEach((k, v) -> {

                        HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                        hashOperations.increment("inventory:", k, v);

                        System.out.println(k + " " + v);
                        //specMapper.updateInventory(k,v);

                        Spec spec = new Spec();
                        spec.setSpecId(k);
                        spec.setNowInventory(v);

                        specMapper.updateInventory(spec);

                    });

                    redisTemplate.delete(finalPay+"order:user:identifier:" + cartDto.getUserId());
                    redisTemplate.delete(finalPay+"order:" + cartDto.getUserId());//这里有竞态条件 过了十分钟要过期时 运行到这句 有新订单进来 会触发重复返库存//

                    redisTemplate.delete(finalPay+"order:user:orderId:"+cartDto.getUserId());
                }
            }
            redisLock.releaseLock(finalPay+"user:incr:order:"+cartDto.getUserId(),pairDto.getSecond());
        });

    }

    /*
    @Transactional//减数据库库存
    public void decrInventoryDb(SimpleCartDto cartDto){
        Spec spec = specMapper.selectForUpdateById(cartDto.getGoodsId());
        spec.setNowInventory(spec.getNowInventory()-cartDto.getQuantity());
        //spec.setSales(spec.getSales()+cartDto.getQuantity());
        specMapper.updateByPrimaryKeySelective(spec);

        //还需要操作订单表
    }
    */

    //@Transactional//减数据库库存 生成订单
    public void decrInventoryDb(SimpleCartDto cartDto){

        if(cartDto.getOrder()){

            Long orderId = cartDto.getOrderId();
            //Long orderId = IdUtil.nextId();
            //Date createDate = new Date();
            Date createDate = cartDto.getCreateDate();

            Order order = Order.newBuilder().orderId(orderId).createDate(createDate).userId(cartDto.getUser().getUserId())
                    .userName(cartDto.getUser().getUserName()).statusCode(10).statusName("已下单").build();

            //需要有一个关于运费的策略

            //BigDecimal itemTotal = new BigDecimal(0);
            BigDecimal[] itemTotal = {new BigDecimal(0)};

            List<OrderItemSpec> orderItemSpecList = new ArrayList<>(10);

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
            orderMapper.insertSelective(order);

            Logistics logistics = new Logistics();
            logistics.setCreateDate(createDate);
            logistics.setOrderId(orderId);
            logistics.setYunfeiCost(yunfeiCost.toString());
            logistics.setUserId(cartDto.getUser().getUserId());
            //logisticsMapper.insertSelective(logistics);
            logisticsMapper.insertSelectiveOrUpdateSelective(logistics);


            try {//这两个的目的 是为了重定向到订单页时为了不重新计算
                redisTemplate.opsForValue().set("order:list:" + cartDto.getUserId(), objectMapper.writeValueAsString(orderItemSpecList),10,TimeUnit.MINUTES);
                redisTemplate.opsForValue().set("order:stat:"+cartDto.getUserId(),objectMapper.writeValueAsString(order),10,TimeUnit.MINUTES);
                redisTemplate.opsForValue().set("order:stat:orderId:"+cartDto.getUserId(),orderId+"",10,TimeUnit.MINUTES);
            }catch (JsonProcessingException ex){
                ex.printStackTrace();
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

                        //还需要事务
                        //要不要再判断库存
                        //orderService.decrInventoryDb(cartDto);

                        handler.run();

                    },
                    (ex) -> {//Recoverer
                        throw new AmqpRejectAndDontRequeueException("Recorver");
                    });
        }catch (Exception ex){
            if(ex instanceof AmqpRejectAndDontRequeueException){
                throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }else{
                //System.out.println(ex);
                //throw new RuntimeException(ex.getMessage(),ex.getCause());

                throw new RuntimeException(ex);
               // throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }

        }
    }

    public void test(){
        Map<Integer,Integer> map = new HashMap<>(16);
        User user = new User();
        user.setUserId(2);

        for(int i = 1;i<3;i++) {
            int ram = new Random().nextInt(3) + 9;
            map.put(i, ram * 10);
            //System.out.println(ram * 10);
        }
        decrInventoryAndSend(map,user);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(!b){
            System.out.println("correlationDataId "+correlationData.getId());
            System.out.println("cause "+s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("returnedMessage");
        System.out.println(message);
    }
    //这个能起效 上面的 convertAndSend 发到错误的routingKey 这里就会触发

    //@PostConstruct
    public void inventoryToRedis(){
        System.out.println("PostConstruct");

        int resultNum = specMapper.selectCount();
        int total = resultNum/20;
        if(resultNum%20 != 0){
            total++;
        }

        for(int i = 1;i<=total;i++) {
            PageHelper.startPage(i, 20, false);
            Page<Spec> list = specMapper.selectAllOnlyIdAndInventory();//这里要不要分页取 一页20个
            //要不要再用上线程池

            Map<Integer, Integer> map = new HashMap<>(16);
            for (Spec spec : list) {
                map.put(spec.getSpecId(), spec.getNowInventory());
            }

            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll("inventory:", map);
        }

        //Spec spec = specMapper.selectByPrimaryKey(2);
        //spec.setNowInventory(spec.getNowInventory()-800);
        //specMapper.updateByPrimaryKeySelective(spec);

        //specMapper.updateInventory(2,800);
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
        Map<Integer,Integer> map = hashOperations.entries("order:"+user.getUserId());
        if(map!=null && !map.isEmpty()){
            String orderListJson = redisTemplate.opsForValue().get("order:list:"+user.getUserId());
            String orderStatJson = redisTemplate.opsForValue().get("order:stat:"+user.getUserId());
            String orderId = redisTemplate.opsForValue().get("order:user:orderId:"+user.getUserId());
            String orderId1 = redisTemplate.opsForValue().get("order:stat:orderId:"+user.getUserId());


            List<OrderItemSpec> orderItemSpecList = null;
            Order order = null;

            List<Integer> idList = new ArrayList<>(10);


            if(orderListJson!=null && orderStatJson!=null&&orderId1!=null&&orderId1.equals(orderId)){//这样也不行 redis中的旧值存在的话 这个这个判断也通过 下面也有一处这样
                //为了让这个判断行 异步操作前 就删除旧值

                try {
                    orderItemSpecList = objectMapper.readValue(orderListJson, new TypeReference<ArrayList<OrderItemSpec>>() {});
                    order = objectMapper.readValue(orderStatJson,Order.class);

                    for (OrderItemSpec orderItemSpec:orderItemSpecList){
                        idList.add(orderItemSpec.getItemId());
                    }

                }catch (IOException ex){
                    ex.printStackTrace();
                }

            }else{
                //long stat = System.currentTimeMillis();

                System.out.println("kkk");
                BigDecimal itemTotal = new BigDecimal(0);
                //List<OrderItemSpec> orderItemSpecList = new ArrayList<>(10);
                orderItemSpecList = new ArrayList<>(10);
                List<Spec> specs = specMapper.selectAllSpecById(new ArrayList<>(map.keySet()));
                //List<Integer> idList = new ArrayList<>(10);
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

                //Order order = new Order();
                order = new Order();
                order.setItemTotal(itemTotal);
                BigDecimal yunfeiCost = new BigDecimal(10);
                order.setYunfeiCost(yunfeiCost);
                order.setPayTotal(itemTotal.add(yunfeiCost));

                //System.out.println(System.currentTimeMillis()-stat);
            }

            List<Item> items = itemMapper.selectAllItemById(idList);
            //System.out.println(items);
            Map<Integer, Item> map1 = new HashMap<>(16);
            for (Item item : items) {
                map1.put(item.getItemId(), item);
            }

            for (OrderItemSpec orderItemSpec : orderItemSpecList) {
                //System.out.println(map1.get(orderItemSpec.getItemId()).getImageUrl());
                orderItemSpec.setImageUrl(map1.get(orderItemSpec.getItemId()).getImageUrl());
            }

            PairDto<Order,List<OrderItemSpec>,String> pairDto = new PairDto<>();
            pairDto.setFirst(order);
            pairDto.setSecond(orderItemSpecList);

            String identifier = redisTemplate.opsForValue().get("order:user:identifier:"+user.getUserId());
            //String orderId = redisTemplate.opsForValue().get("order:user:orderId:"+user.getUserId());
            pairDto.setThird(identifier+"@@@"+orderId);

            return pairDto;
        }
        System.out.println("null");
        return null;
    }

    //还要校验是不是数字
    public Integer validateAddressAndInsert(String trueName,String cityId,
                                            String areaId,String areaInfo,String address,String mobPhone){
        if(cityId.equals("-请选择-")){
            China china = chinaMapper.selectById(Integer.valueOf(areaId));
            if(china==null){
                //丢异常
                throw new RuntimeException("地区不存在");
            }else{
                Map<String,String> map = new HashMap<>(16);
                map.put("province",china.getName());
                return setMap(map,trueName,address,areaInfo,mobPhone);
            }
        }
        if(cityId.equals(areaId)){
            China china = chinaMapper.selectById(Integer.valueOf(areaId));
            if(china==null){
                //丢异常
                throw new RuntimeException("地区不存在");
            }else{
                China china1 = chinaMapper.selectById(china.getParentId());
                Map<String,String> map = new HashMap<>(16);
                map.put("province",china1.getName());
                map.put("city",china.getName());
                return setMap(map,trueName,address,areaInfo,mobPhone);
            }

        }
        China china = chinaMapper.selectById(Integer.valueOf(cityId));
        China china1 = chinaMapper.selectById(Integer.valueOf(areaId));
        if(china==null||china1==null){
            //丢异常
            throw new RuntimeException("地区不存在");
        }else{
            China china2 = chinaMapper.selectById(china.getParentId());
            Map<String,String> map = new HashMap<>(16);
            map.put("province",china2.getName());
            map.put("city",china.getName());
            map.put("district",china1.getName());
            return setMap(map,trueName,address,areaInfo,mobPhone);
        }
    }

    private Integer setMap(Map<String,String> map,String trueName,String address,String areaInfo,String mobPhone){
        map.put("name",trueName);
        map.put("address",address);
        map.put("areaInfo",areaInfo);//相当于概括
        map.put("phoneNumber",mobPhone+"");
        Long id = redisTemplate.opsForValue().increment("temp:logistics:id",1);
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll("temp:logistics:"+id,map);
        redisTemplate.expire("temp:logistics:"+id,10,TimeUnit.MINUTES);
        return id.intValue();
    }


    public void insert(List<List<String>> lists,Integer id){
        List<China> chinaList = new ArrayList<>(10);
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
        //看redis中的订单是否还在

        //redisTemplate.hasKey("order:user:identifier:"+user.getUserId());
        //redisTemplate.hasKey("order:"+user.getUserId());

        /*
        HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
        Map<Integer,Integer> redisMap = hashOperations.entries("order:"+user.getUserId());//转成json 比较两个map元素是否相等
        if(redisMap!=null&&!redisMap.isEmpty()){
            try {
                String redisMapString = objectMapper.writeValueAsString(redisMap);
                String mapString = objectMapper.writeValueAsString(map);
            }catch (JsonProcessingException ex){
                ex.printStackTrace();
            }
        }
        */


        Map<Integer,Integer> map1 = null;
        //锁
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("user:incr:order:"+user.getUserId());
        if(pairDto.getFirst()) {
            String identifier = redisTemplate.opsForValue().get("order:user:identifier:" + user.getUserId());
            //System.out.println(identifier);
            //System.out.println(token);
            //System.out.println("----");
            if (identifier == null || !identifier.equals(token)) {
                redisLock.releaseLock("user:incr:order:"+user.getUserId(),pairDto.getSecond());
                Map<Integer, String> quantityInfo = decrInventoryAndSend(map, user);
                System.out.println("qaz");

                //注意如果是订单过期了 重新生成订单 下面的 Logistics logistics = logisticsMapper.selectByUserIdOrderByIdLimitOne(user.getUserId());
                //可能会取到错的
                //因为 生成订单 是通过消息队列异步发到数据库的

                if (quantityInfo != null && !quantityInfo.isEmpty()) {
                    return quantityInfo;
                }


                while (true){//循环 等到数据库操作完成
                    if (redisTemplate.hasKey("order:list:" + user.getUserId())) {//这样也不行 redis中的旧值存在的话 这个这个判断也通过
                        //为了让这个判断行 异步操作前 就删除旧值
                        break;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
            System.out.println(map);
            map1 = hashOperations.entries("order:"+user.getUserId());
            System.out.println(map1);
            System.out.println(orderId);
            orderId = redisTemplate.opsForValue().get("order:user:orderId:"+ user.getUserId());
            System.out.println(orderId);

            redisTemplate.delete("order:user:identifier:" + user.getUserId());
            redisTemplate.delete("order:" + user.getUserId());
            redisTemplate.delete("order:user:orderId:"+ user.getUserId());

            //释放锁
            redisLock.releaseLock("user:incr:order:"+user.getUserId(),pairDto.getSecond());
        }

        decrInventoryAndSendForPay(map1,user,orderId);

        HashOperations<String,String,String> stringHashOperations = redisTemplate.opsForHash();
        Map<String,String> tempLogistics = stringHashOperations.entries("temp:logistics:"+addressId);


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

        //根据userId 找到这个user最新的订单
        //Logistics logistics = logisticsMapper.selectByUserIdOrderByIdLimitOne(user.getUserId());

        //Logistics logistics = logisticsMapper.selectByOrderId(Long.parseLong(orderId));
        Logistics logistics = new Logistics();
        logistics.setOrderId(Long.parseLong(orderId));
        logistics.setProvince(tempLogistics.get("province"));
        logistics.setCity(tempLogistics.get("city"));
        logistics.setDistrict(tempLogistics.get("district"));
        logistics.setName(tempLogistics.get("name"));
        logistics.setAddress(tempLogistics.get("address"));
        logistics.setAddressInfo(tempLogistics.get("areaInfo"));
        logistics.setPhoneNumber(tempLogistics.get("phoneNumber"));
        //logisticsMapper.updateByOrderId(logistics);
        logisticsMapper.insertSelectiveOrUpdateSelective(logistics);

        redisTemplate.delete("temp:logistics:"+addressId);

        return null;
    }

    public void decrInventoryAndSendForPay(Map<Integer, Integer> map, User user,String orderId) {//没有检验map数量
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("pay:user:decr:order:"+user.getUserId());
        //这里用分布式锁当限流用

        if(pairDto.getFirst()){

            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            Map<Integer, Integer> orderRedisMap = hashOperations.entries("pay:order:" + user.getUserId());

            PairDto<Boolean,String,Object> pairDto1 = redisLock.acquireLockWithTimeout("pay:user:incr:order:"+user.getUserId());
            if(pairDto1.getFirst()) {
                if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                    redisLock.releaseLock("pay:user:incr:order:"+user.getUserId(),pairDto1.getSecond());
                    decrInventoryRedis(hashOperations, user,map,orderId);
                } else {

                    //redis返回库存
                    redisTemplate.executePipelined(new RedisCallback<Object>() {
                        @Override
                        public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                            orderRedisMap.forEach((k, v) -> {
                                redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")), (k + "").getBytes(Charset.forName("UTF-8")), v);
                            });

                            return null;
                        }
                    });

                    redisTemplate.delete("pay:order:" + user.getUserId());
                    redisTemplate.delete("pay:order:user:identifier:" + user.getUserId());
                    redisTemplate.delete("pay:order:user:orderId:" + user.getUserId());

                    redisLock.releaseLock("pay:user:incr:order:"+user.getUserId(),pairDto1.getSecond());

                    Map<Integer, Integer> orderRedisMapReverse = new HashMap<>(16);
                    orderRedisMap.forEach((k, v) -> {
                        orderRedisMapReverse.put(k, -v);
                    });
                    decrInventoryAndSend2(orderRedisMapReverse, user, false,null);//数据库返回库存

                    decrInventoryRedis(hashOperations, user,map,orderId);
                }
            }

            redisLock.releaseLock("pay:user:decr:order:"+user.getUserId(),pairDto.getSecond());

        }//没有处理抢不到锁操作

    }

    //准备延时任务的数据 30min返库存
    private void decrInventoryRedis(HashOperations<String,Integer,Integer> hashOperations,User user,Map<Integer,Integer> map,String orderId){

        hashOperations.putAll("pay:order:"+user.getUserId(),map);//redis中放要支付的订单

        redisTemplate.expire("pay:order:"+user.getUserId(),30,TimeUnit.MINUTES);
        String identifier = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("pay:order:user:identifier:"+user.getUserId(),identifier);
        redisTemplate.opsForValue().set("pay:order:user:orderId:"+user.getUserId(),orderId);

        SimpleCartDto simpleCartDto = new SimpleCartDto();
        simpleCartDto.setUserId(user.getUserId());
        simpleCartDto.setIdentifier(identifier);
        simpleCartDto.setMap(map);

        CorrelationData correlationData = new CorrelationData(identifier);
        rabbitTemplate.convertAndSend("incrInventory30min", simpleCartDto, correlationData);

    }

    //@RabbitListener(queues = "deadQueueIncrInventory30min")
    public void receiveAndIncrInventory30min(SimpleCartDto cartDto){
        receiveAndIncrInventory(cartDto,"pay:");
    }

    //@Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //@Autowired
    public void setRedisLock(RedisLock redisLock) {
        this.redisLock = redisLock;
    }

    //@Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //@Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }

    //@Autowired
    public void setOrderService(OrderServiceImpl3 orderService) {
        this.orderService = orderService;
    }

    //@Autowired
    public void setOrderItemSpecMapper(OrderItemSpecMapper orderItemSpecMapper) {
        this.orderItemSpecMapper = orderItemSpecMapper;
    }

    //@Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    //@Autowired
    public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }

    //@Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //@Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    //@Autowired
    public void setChinaMapper(ChinaMapper chinaMapper) {
        this.chinaMapper = chinaMapper;
    }
}
