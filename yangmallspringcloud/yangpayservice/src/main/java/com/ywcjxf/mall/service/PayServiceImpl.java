package com.ywcjxf.mall.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.dao.OrderMapper;
import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.service.exception.NoSuchOrderUser;
import com.ywcjxf.mall.service.pay.config.PayJSConfig;
import com.ywcjxf.mall.service.pay.config.util.HttpInvoker;
import com.ywcjxf.mall.service.pay.config.util.SignUtil;
import com.ywcjxf.mall.service.pay.config.util.UnicodeUtil;
import com.ywcjxf.mall.service.util.RedisReentrantLock;
import com.ywcjxf.mall.serviceinterface.PayService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.ywcjxf.mall.constant.Cart.CART_REDIS_PREFIX;
import static com.ywcjxf.mall.service.constant.OrderAndPayConstants.*;

@Service
public class PayServiceImpl implements PayService {
    private PayJSConfig payJSConfig;

    private RedisTemplate<String,String> redisTemplate;
    private ObjectMapper objectMapper;

    private OrderMapper orderMapper;

    private RedisReentrantLock redisReentrantLock;

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);


    public  Order orderIdAndAmount(User user){
        /*
        RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout("pay:user:order"+user.getUserId());
        if(lockEntity.isAcquire()){
            try{

                if(redisTemplate.hasKey("pay:order:" + user.getUserId())) {
                    Long orderId = Long.parseLong(redisTemplate.opsForValue().get("pay:order:user:orderId:" + user.getUserId()));
                    return orderMapper.selectByOrderId(orderId);
                }
                return null;

            }finally {
                lockEntity.releaseLock();
            }

        }//没有处理取不到锁的
        return null;
        */

        String orderIdString = redisTemplate.opsForValue().get(PAY_ORDER_USER_ORDER_ID + user.getUserId());
        if(orderIdString!=null){
            Long orderId = Long.parseLong(redisTemplate.opsForValue().get(PAY_ORDER_USER_ORDER_ID + user.getUserId()));
            return orderMapper.selectByOrderId(orderId);
        }
        return null;
    }

    //同一个订单 用户切换支付方式 返回的字符串是 order_exist
    //java.lang.NumberFormatException: For input string: "der_exist"

    public PairDto<Order,PairDto<String,String,String>,Object> showCode(User user,String method,Long orderId){
        RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(LOCK_PAY_USER_ORDER+user.getUserId());
        if(lockEntity.isAcquire()){
            try{

                if(redisTemplate.hasKey(PAY_ORDER + user.getUserId())) {
                    orderId = Long.parseLong(redisTemplate.opsForValue().get(PAY_ORDER_USER_ORDER_ID + user.getUserId()));
                    Order order =  orderMapper.selectByOrderId(orderId);

                    Map<String,String> map = new LinkedHashMap<>(16);
                    map.put("name","订单"+order.getOrderId());
                    map.put("pay_type",method);
                    map.put("price",order.getPayTotal().toString());
                    //String out_trade_no = "order"+System.currentTimeMillis();
                    String out_trade_no = order.getOrderId()+"";
                    map.put("order_id",out_trade_no);
                    map.put("notify_url","http://www.baidu.com");
                    String md5 = SignUtil.sign(map, payJSConfig.getKey());
                    //System.out.println("secr: "+md5);
                    logger.info("secr: "+md5);
                    map.put("sign", md5);
                    map.put("order_uid",user.getUserId()+"");

                    //int ram = ThreadLocalRandom.current().nextInt(900000)+100000;
                    //map.put("more",ram+"");
                    //这个more要不要对于订单每一个都是独特的 之后入库 或者存redis
                    //不确定重复提交了 支付平台当成同一份订单 这个more还会不会变化

                    Long ttl = redisTemplate.getExpire(PAY_ORDER+user.getUserId());

                    //map.put("expire","1800");
                    map.put("expire",ttl+"");

                    String result = HttpInvoker.readContentFromPost("https://xorpay.com/api/pay/"+payJSConfig.getMchid(), map);
                    //支付平台那里有做幂等
                    //好像是加密的那几个字段一样的话 就是同一份订单 然后过期时间不会变化

                    //System.out.println("result: "+result);
                    logger.info("result: "+result);
                    JSONObject jsonObject = JSONObject.parseObject(result);

                    PairDto<String,String,String> pairDto = new PairDto<>();

                    if(null != jsonObject && jsonObject.containsKey("info")){
                        pairDto.setFirst((String)jsonObject.getJSONObject("info").get("qr"));
                        String payPlatformNumber = jsonObject.getString("aoid");
                        pairDto.setSecond(payPlatformNumber);
                        //支付平台好aoid要不要入库 或者存redis

                        order.setPayPlatformNumber(payPlatformNumber);
                        orderMapper.updateByOrderId(order);

                        redisTemplate.opsForValue().set(PAY_ORDER_USER_PAY_PLATFORM_NUMBER + user.getUserId(),payPlatformNumber,THIRTY_MIN, TimeUnit.MINUTES);
                        //redisTemplate.opsForValue().set("pay:order:user:more:" + user.getUserId(),ram+"",30, TimeUnit.MINUTES);

                        //redisTemplate.opsForValue().set("pay:order:user:payPlatformNumber:" + user.getUserId(),out_trade_no,ttl, TimeUnit.SECONDS);
                        //redisTemplate.opsForValue().set("pay:order:user:more:" + user.getUserId(),ram+"",30, TimeUnit.SECONDS);

                        /*
注意这几种status
pay_type_error 支付类型错误
order_payed 订单已支付
order_expire 订单过期
                         */

                    } else if(null != jsonObject && jsonObject.containsKey("status")){
                        //String reason = UnicodeUtil.decodeUnicode(jsonObject.getString("status"));
                        pairDto.setThird(UnicodeUtil.decodeUnicode(jsonObject.getString("status")));
                    }

                    PairDto<Order,PairDto<String,String,String>,Object> pairDto1 = new PairDto<>();
                    pairDto1.setFirst(order);
                    pairDto1.setSecond(pairDto);

                    return pairDto1;
                }
                return null;

            }finally {
                lockEntity.releaseLock();
            }
        }
        logger.error("can't get lock");
        return null;

    }

    public void payNotify(String aoId,Long orderId,String payPrice,String payTime,
                          String detail,String more/*,User user*/){

        Order orderForUserId = orderMapper.selectByOrderIdAndPayPlatformNumber(orderId,aoId);
        if(orderForUserId==null){
            throw new NoSuchOrderUser();
        }
        User user = new User();
        user.setUserId(orderForUserId.getUserId());

        String result = HttpInvoker.readContentFromGet("https://xorpay.com/api/query/"+aoId);
        JSONObject jsonObject = JSONObject.parseObject(result);

        String status = jsonObject.getString("status");
        if(status.equals("success")) {
            //System.out.println(status);

            logger.info(status);

/*
payed 订单已支付，未通知成功
success 订单已支付，通知成功

要不要处理payed
 */
            RedisReentrantLock.LockEntity lockEntity = redisReentrantLock.acquireLockWithTimeout(LOCK_PAY_USER_ORDER+user.getUserId());
            if(lockEntity.isAcquire()){
                try{

                    String redisOrderIdString = redisTemplate.opsForValue().get(PAY_ORDER_USER_ORDER_ID + user.getUserId());
                    String redisPayPlatformNumber = redisTemplate.opsForValue().get(PAY_ORDER_USER_PAY_PLATFORM_NUMBER + user.getUserId());
                    //String redisMore = redisTemplate.opsForValue().get("pay:order:user:more:" + user.getUserId());

                    //这里其实没有校验 只是看和redis中的订单是不是同一个
                    if(redisOrderIdString!=null && redisOrderIdString.equals(orderId+"") && redisPayPlatformNumber!=null && redisPayPlatformNumber.equals(aoId)
                            /*&& redisMore!=null && redisMore.equals(more)*/){

                        //还要不要校验payPrice和 detail
                        //System.out.println("payPrice: "+payPrice);
                        //System.out.println("detail: "+detail);

                        logger.info("payPrice: "+payPrice);
                        logger.info("detail: "+detail);

                        Order order = new Order();

                        Date date = null;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            date = simpleDateFormat.parse(payTime);
                        }catch (ParseException ex){
                            //ex.printStackTrace();

                            logger.error("simpleDateFormat"+"_"+ex.getMessage(),ex);
                        }

                        order.setPayDate(date);
                        order.setStatusCode(20);
                        order.setStatusName("已支付");
                        //order.setPayPlatformNumber();
                        //order.setWaitPayDate();

                        order.setOrderId(orderId);
                        orderMapper.updateByOrderId(order);

                        /*
                        支付成功删除购物车

要不要匹配订单物品和购物车物品的相似性
完全一样就把购物车删除
但是这样的话 购物车页那边的业务代码就要上锁

还是不管一不一样 直接把购物车删除

                         */

                        HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
                        Map<Integer,Integer> cartMap = hashOperations.entries(CART_REDIS_PREFIX+user.getUserId());
                        if(cartMap!=null){
                            try {
                                String cartMapString = objectMapper.writeValueAsString(cartMap);
                                String orderMapString = objectMapper.writeValueAsString(hashOperations.entries(PAY_ORDER + user.getUserId()));
                                if(cartMapString.equals(orderMapString)){
                                    redisTemplate.delete(CART_REDIS_PREFIX+user.getUserId());
                                }
                            }catch (JsonProcessingException ex){
                                //ex.printStackTrace();

                                logger.error("objectMapper"+"_"+ex.getMessage(),ex);
                            }
                        }

                        redisTemplate.delete(PAY_ORDER + user.getUserId());
                        redisTemplate.delete(PAY_ORDER_USER_IDENTIFIER + user.getUserId());
                        redisTemplate.delete(PAY_ORDER_USER_ORDER_ID + user.getUserId());

                        redisTemplate.delete(PAY_ORDER_USER_PAY_PLATFORM_NUMBER + user.getUserId());
                        //redisTemplate.delete("pay:order:user:more:" + user.getUserId());

                        redisTemplate.opsForValue().set(PAY_ORDER_USER_SUCCESS+user.getUserId(),"true",THIRTY_MIN,TimeUnit.MINUTES);
                        //这个过期时间要不要从"pay:order:" + user.getUserId()里取


                    }else{

                                //想好退款条件
                                //不然订单状态是已支付，redis中已经删除订单了
                                //但是还退款 就出问题了

                                Order oldOrder = orderMapper.selectByOrderIdAndPayPlatformNumber(orderId,aoId);
                                //if((oldOrder!=null && oldOrder.getStatusCode()>=20) || oldOrder==null) {}

                                //if((oldOrder==null || oldOrder.getStatusCode()<20) && oldOrder!=null) {
                                if(oldOrder!=null && oldOrder.getStatusCode()<20){
                                //}else{
                                    //退款
                                    Map<String, String> map = new LinkedHashMap<>(16);
                                    map.put("price", payPrice);//用这个还是用从数据库查出来的
                                    map.put("sign", SignUtil.sign(map, payJSConfig.getKey()));
                                    String postResult = HttpInvoker.readContentFromPost("https://xorpay.com/api/refund/" + aoId, map);
                                    //System.out.println(postResult);
                                    logger.info(postResult);

                                }
                    }

                }finally {
                    lockEntity.releaseLock();
                }
            }//没有处理获取不到锁的操作
        }//以下是status不等于success

    }

    public boolean paySuccessIsTrue(String aoId,User user){
        //Order order = orderMapper.selectByPayPlatformNumber(aoId);
        /*
        if(order!=null && order.getStatusCode()>=20){
            return true;
        }

        return false;
        */

        //return order!=null && order.getStatusCode()>=20;

        String booleanString = redisTemplate.opsForValue().get(PAY_ORDER_USER_SUCCESS+user.getUserId());
        if(booleanString!=null&&booleanString.equals("true")){
            redisTemplate.delete(PAY_ORDER_USER_SUCCESS+user.getUserId());
            return true;
        }
        return false;
    }

    @Autowired
    public void setPayJSConfig(PayJSConfig payJSConfig) {
        this.payJSConfig = payJSConfig;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setRedisReentrantLock(RedisReentrantLock redisReentrantLock) {
        this.redisReentrantLock = redisReentrantLock;
    }
}
