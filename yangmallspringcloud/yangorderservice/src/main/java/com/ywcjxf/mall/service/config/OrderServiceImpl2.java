package com.ywcjxf.mall.service.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ywcjxf.mall.dao.SpecMapper;
import com.ywcjxf.mall.pojo.*;
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


//@Service
public abstract class OrderServiceImpl2 implements OrderService,RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    private RabbitTemplate rabbitTemplate;
    private RedisLock redisLock;
    private RedisTemplate<String,String> redisTemplate;
    private SpecMapper specMapper;

    private OrderServiceImpl2 orderService;

    /*
    @Override
    public Map<Integer,String> decrInventoryAndSend(Map<Integer, Integer> map, User user) {//没有检验map数量

        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("user:decr:order:"+user.getUserId());
        //这里用分布式锁当限流用

        if(pairDto.getFirst()){
            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            Map<Integer, Integer> orderRedisMap = hashOperations.entries("order:" + user.getUserId());

            Map<Integer, String> quantityInfo = new HashMap<>(16);
            if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                map.forEach((k, v) -> {
                    decrInventoryRedis(hashOperations, k, v, user, quantityInfo);
                });
            } else {
                Set<Integer> set = map.keySet();
                Set<Integer> orderRedisSet = orderRedisMap.keySet();

                Set<Integer> exceptSet = new HashSet<>(set);
                exceptSet.removeAll(orderRedisSet);

                Set<Integer> intersectSet = new HashSet<>(set);
                intersectSet.retainAll(orderRedisSet);

                Set<Integer> exceptSet2 = new HashSet<>(orderRedisSet);
                exceptSet2.removeAll(set);

                for (Integer k : exceptSet) {
                    decrInventoryRedis(hashOperations, k, map.get(k), user, quantityInfo);
                }

                for (Integer k : intersectSet) {
                    int plus = map.get(k) - orderRedisMap.get(k);
                    if (plus != 0) {
                        decrInventoryRedis(hashOperations, k, plus, user, quantityInfo);
                    }
                }

                for (Integer k : exceptSet2) {
                    decrInventoryRedis(hashOperations, k, -map.get(k), user, quantityInfo);
                }
            }

            redisLock.releaseLock("user:decr:order:"+user.getUserId(),pairDto.getSecond());

            return quantityInfo;
        }//没有处理抢不到锁操作

        return null;
    }
    */

    @Override
    public Map<Integer,String> decrInventoryAndSend(Map<Integer, Integer> map, User user) {//没有检验map数量

        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("user:decr:order:"+user.getUserId());
        //这里用分布式锁当限流用

        if(pairDto.getFirst()){
            HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
            Map<Integer, Integer> orderRedisMap = hashOperations.entries("order:" + user.getUserId());

            Map<Integer, String> quantityInfo = new HashMap<>(16);
            if (orderRedisMap == null || orderRedisMap.isEmpty()) {
                map.forEach((k, v) -> {
                    decrInventoryRedis(hashOperations, k, v, user, quantityInfo);
                });
            } else {

                /*
                long start = System.currentTimeMillis();
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        orderRedisMap.forEach((k, v) -> {
                            redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")),(k+"").getBytes(Charset.forName("UTF-8")),v);
                        });

                        return null;
                    }
                },true,true);
                System.out.println(System.currentTimeMillis()-start);
                */

                //long start = System.currentTimeMillis();
                redisTemplate.executePipelined(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        orderRedisMap.forEach((k, v) -> {
                            redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")), (k + "").getBytes(Charset.forName("UTF-8")), v);
                        });

                        return null;
                    }
                });
                //System.out.println(System.currentTimeMillis()-start);

                redisTemplate.delete("order:" + user.getUserId());

                //上面是redis中 返回库存 这里是数据库里返回库存
                orderRedisMap.forEach((k, v) -> {
                    incrInventoryAndSend2(k, -v, user);
                });

                map.forEach((k, v) -> {
                    decrInventoryRedis(hashOperations, k, v, user, quantityInfo);
                });
            }

            redisLock.releaseLock("user:decr:order:"+user.getUserId(),pairDto.getSecond());

            return quantityInfo;
        }//没有处理抢不到锁操作

        return null;
    }

    //发 用于加redis库存 和数据库库存
    private void incrInventoryAndSend(Integer k,Integer v,User user){
        SimpleCartDto cartDto = new SimpleCartDto();
        cartDto.setGoodsId(k);
        cartDto.setQuantity(v);

        cartDto.setUserId(user.getUserId());

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("incrInventory", cartDto, correlationData);
    }

    //发 用于加redis库存 和数据库库存
    public void incrInventoryAndSend(SimpleCartDto cartDto){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("incrInventory", cartDto, correlationData);
    }

    //发 用于加数据库库存
    private void incrInventoryAndSend2(Integer k,Integer v,User user){
        SimpleCartDto cartDto = new SimpleCartDto();
        cartDto.setGoodsId(k);
        cartDto.setQuantity(v);
        cartDto.setUserId(user.getUserId());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("order", cartDto, correlationData);
    }

    //减redis库存 和 发 用于减数据库库存 和 发 用于加redis库存和数据库库存
    private void decrInventoryRedis(HashOperations<String,Integer,Integer> hashOperations,Integer k,Integer v,User user,Map<Integer,String> quantityInfo){
        PairDto<Boolean,String,Object> pairDto = redisLock.acquireLockWithTimeout("spec:"+k);
        if(pairDto.getFirst()) {
            Integer inv = hashOperations.get("inventory:", k);
            if (v <= inv) {
                hashOperations.increment("inventory:", k, -v);

                SimpleCartDto cartDto = new SimpleCartDto();
                cartDto.setGoodsId(k);
                cartDto.setQuantity(v);

                cartDto.setUserId(user.getUserId());

                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("order", cartDto, correlationData);

                redisLock.releaseLock("spec:"+k,pairDto.getSecond());

                hashOperations.increment("order:"+user.getUserId(),k,v);

                //redisTemplate.expire("order:" + user.getUserId(), 10, TimeUnit.MINUTES);
                //incrInventoryAndSend(cartDto);
            }else{
                quantityInfo.put(k,"库存不足，最大库存为"+inv);
                redisLock.releaseLock("spec:"+k,pairDto.getSecond());
            }
        }
    }


    //@Override
    /*
    @RabbitListener(queues = "order")
    public void receiveAndDecrInventory(SimpleCartDto cartDto, Channel channel,Message message) {
        System.out.println(cartDto.getGoodsId());
        System.out.println(cartDto.getQuantity());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    */

    /*
    @RabbitListener(queues = "order")
    public void receiveAndDecrInventory(SimpleCartDto cartDto) {

        Map<Object,Boolean> map = new HashMap<>(16);
        map.put(IOException.class,true);
        try {
            RetryUtil.retry(3, 1000, map,
                    () -> {//Handler
                        System.out.println(cartDto.getGoodsId());
                        System.out.println(cartDto.getQuantity());
                        throw new IOException();
                    },
                    () -> {//Recoverer
                        throw new AmqpRejectAndDontRequeueException("Recorver");
                    });
        }catch (Exception ex){
            if(ex instanceof AmqpRejectAndDontRequeueException){
                throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }else{
                throw new RuntimeException();
            }

        }

    }
    */

    //@RabbitListener(queues = "order")//收 用于减数据库库存
    public void receiveAndDecrInventory(SimpleCartDto cartDto) {
        //System.out.println("hhh");

        Map<Object,Boolean> map = new HashMap<>(16);
        try {
            RetryUtil.retry(0, 1000, map,
                    () -> {//Handler
                        //核心代码在这一块

                        //还需要事务
                        //要不要再判断库存
                        orderService.decrInventoryDb(cartDto);

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

                //throw new RuntimeException(ex);
                throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }

        }

    }

    //收 用于加redis库存 和数据库库存
    //@RabbitListener(queues = "deadQueueIncrInventory")
    public void receiveAndIncrInventory(SimpleCartDto cartDto){
        Map<Object,Boolean> map = new HashMap<>(16);
        try {
            RetryUtil.retry(0, 1000, map,
                    () -> {//Handler
                        //redis中返回库存
                        //数据库中返回库存

                        //这里不用 先取出来 增加 再更新回去
                        //直接用sql 更新

                        HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                        hashOperations.increment("inventory:",cartDto.getGoodsId(),cartDto.getQuantity());

                        //specMapper.updateInventory(cartDto.getQuantity(),cartDto.getGoodsId());
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
                //throw new AmqpRejectAndDontRequeueException(ex.getMessage(),ex.getCause());
            }

        }

    }

    @Transactional//减数据库库存
    public void decrInventoryDb(SimpleCartDto cartDto){
        //System.out.println("qqq");

        //System.out.println(cartDto.getUserId());
        Spec spec = specMapper.selectForUpdateById(cartDto.getGoodsId());

        /*
        if(spec.getNowInventory()<cartDto.getQuantity()){//这个判断要不要
            throw new RuntimeException("库存不足");
        }
        */

        spec.setNowInventory(spec.getNowInventory()-cartDto.getQuantity());
        //spec.setSales(spec.getSales()+cartDto.getQuantity());
        specMapper.updateByPrimaryKeySelective(spec);
        //spec.setSales(spec.getSales()+cartDto.getQuantity());

        //还需要操作订单表
    }

    public void test(){
        /*
        Map<Integer,Integer> map = new HashMap<>(16);
        User user = new User();
        user.setUserId(9);

        for(int i = 1;i<3;i++) {
            int ram = new Random().nextInt(3) + 9;
            map.put(i, ram * 10);
            System.out.println(ram * 10);
        }
        decrInventoryAndSend(map,user);
        */

        long start = System.currentTimeMillis();
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i=0;i<1000;i++){
                    redisConnection.hIncrBy("inventory:".getBytes(Charset.forName("UTF-8")), (100 + "").getBytes(Charset.forName("UTF-8")), 1);
                }
                return null;
            }
        });
        System.out.println(System.currentTimeMillis()-start);

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
    }

    /*
    public PairDto<Order,List<OrderItemSpec>,Object> getOrder(User user){
        if(redisTemplate.hasKey("order:"+user.getUserId())){
            String orderListJson = null;
            List<OrderItemSpec> orderItemSpecList = null;
            Order order = null;


            //System.out.println(user.getUserId()+"userId");
            //while (orderListJson==null){
                //orderListJson = redisTemplate.opsForValue().get("order:list:"+user.getUserId());
            //}


            while (true){
                System.out.println(user.getUserId()+"userId");
                orderListJson = redisTemplate.opsForValue().get("order:list:"+user.getUserId());
                if(orderListJson!=null){
                    break;
                }
            }

            try {
                orderItemSpecList = objectMapper.readValue(orderListJson, new TypeReference<ArrayList<OrderItemSpec>>() {});
                order = objectMapper.readValue(redisTemplate.opsForValue().get("order:stat:"+user.getUserId()),Order.class);
            }catch (IOException ex){
                ex.printStackTrace();
            }

            PairDto<Order,List<OrderItemSpec>,Object> pairDto = new PairDto<>();
            pairDto.setFirst(order);
            pairDto.setSecond(orderItemSpecList);

            if(orderItemSpecList!=null){
                List<Integer> idList = new ArrayList<>(10);

                for(OrderItemSpec orderItemSpec:orderItemSpecList){
                    orderItemSpec.setEachSpecTotal(orderItemSpec.getRepresentPrice().multiply(new BigDecimal(orderItemSpec.getQuantity())).toString());

                    idList.add(orderItemSpec.getItemId());
                }

                if(!idList.isEmpty()) {
                    List<Item> items = itemMapper.selectAllItemById(idList);//要不要用上缓存
                    Map<Integer,Item> map = new HashMap<>(16);
                    for(Item item:items){
                        map.put(item.getItemId(),item);
                    }

                    for(OrderItemSpec orderItemSpec:orderItemSpecList){
                        orderItemSpec.setImageUrl(map.get(orderItemSpec.getItemId()).getImageUrl());
                    }
                }
            }

            return pairDto;
        }

        return null;
    }
    */


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
    public void setOrderService(OrderServiceImpl2 orderService) {
        this.orderService = orderService;
    }
}
