package com.ywcjxf.mall.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.dao.ItemMapper;
import com.ywcjxf.mall.dao.SpecMapper;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.CartService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.ywcjxf.mall.constant.Cart.CART_REDIS_PREFIX;
import static com.ywcjxf.mall.constant.Cart.THIRTY_DAY;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.DefaultCharset.UTF_8;

@Service
public class CartServiceImpl implements CartService {
    private ObjectMapper mapper;
    private RedisTemplate<String,String> redisTemplate;
    private SpecMapper specMapper;
    private ItemMapper itemMapper;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    //下面两个方法都是用返回值处理异常 ObjectMapper的异常

    //返回false 或者 null controller那里就不设置cookie

    //try包起来的范围有点大

    //可以只try一句 之后的地方 用if来间接判断是不是出异常了

    //用丢出运行时异常来处理库存不足？

    /*
    加入购物车的库存数量 校验

购物车相当于收藏

要校验库存的话相当于
校验当时 购物车中商品数量是不是大于中库存

只判断加入购物车的哪一个的数量是否大于库存

     */


    //下面的方法要不要上分布式锁

    @Override
    public PairDto<Boolean,Integer,String> addItemToRedis(User user, boolean flag, String cookieValue, Integer specId, Integer quantity) {
        //这里item goods 都是指规格 spec

        boolean exceptionStatus = false;

        if(flag) {
            exceptionStatus = cookieToRedis(user,cookieValue,exceptionStatus);
        }

        HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
        hashOperations.increment(CART_REDIS_PREFIX+user.getUserId()+"",specId,quantity);//要校验库存的话再这里校验
        redisTemplate.expire(CART_REDIS_PREFIX+user.getUserId()+"",THIRTY_DAY, TimeUnit.DAYS);

        //return exceptionStatus;

        PairDto<Boolean,Integer,String> pairDto = new PairDto<>();
        pairDto.setFirst(exceptionStatus);

        Map<Integer,Integer> map3 = hashOperations.entries(CART_REDIS_PREFIX+user.getUserId()+"");
        PairDto<Object,Integer,BigDecimal> amount = amount(map3);
        pairDto.setSecond(amount.getSecond());
        pairDto.setThird(amount.getThird().toString());
        return pairDto;
    }

    public boolean cookieToRedis(User user,String cookieValue,boolean exceptionStatus){//exceptionStatus false 有出异常 true 没出异常
        try {
            Map<Integer, Integer> map = mapper.readValue(URLDecoder.decode(cookieValue,UTF_8), new TypeReference<HashMap<Integer, Integer>>(){});
            if (map != null && !map.isEmpty()) {
                HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
                Map<Integer,Integer> map2 = hashOperations.entries(CART_REDIS_PREFIX+user.getUserId()+"");
                if(map2==null){
                    map2 = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
                }

                Map<Integer,Integer> map3 = map2;
                map.forEach((k,v)->{
                    Integer num = map3.get(k);
                    if(num!=null){
                        num = num+v;
                        map3.put(k,num);
                    }else{
                        map3.put(k,v);
                    }
                });

                hashOperations.putAll(CART_REDIS_PREFIX+user.getUserId()+"", map3);
                redisTemplate.expire(CART_REDIS_PREFIX+user.getUserId()+"", THIRTY_DAY, TimeUnit.DAYS);

                exceptionStatus = true;
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
            logger.error(ex.getMessage(),ex);
        }

        return exceptionStatus;
    }

    private PairDto<Object,Integer,BigDecimal> amount(Map<Integer,Integer> map){
        List<Integer> list = new ArrayList<>(map.keySet());
        list.add(-1);

        //System.out.println(list);
        if(logger.isDebugEnabled()){
            logger.debug(list.toString());
        }

        List<Spec> specs = specMapper.selectAllSpecById(list);
        BigDecimal amount =new BigDecimal(0);
        for(Spec spec:specs){
            amount = amount.add(new BigDecimal(map.get(spec.getSpecId())).multiply(spec.getRepresentPrice()));
        }

        PairDto<Object,Integer,BigDecimal> pairDto = new PairDto<>();
        pairDto.setSecond(map.size());
        pairDto.setThird(amount);
        return pairDto;
    }

    @Override
    public PairDto<String,Integer,String> addItemToCookie(boolean flag, String cookieValue, Integer specId, Integer quantity) {
        Map<Integer, Integer> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        String json = null;
        if(flag){
            try {
                map = mapper.readValue(URLDecoder.decode(cookieValue,UTF_8), new TypeReference<HashMap<Integer, Integer>>() {});
                //System.out.println(map);

                if(logger.isDebugEnabled()){
                    logger.debug(map.toString());
                }
                Integer num = map.get(specId);
                /*
                map.forEach((k,v)->{
                    System.out.println(k.getClass());
                    System.out.println(specId.getClass());

                    if(k.equals(specId)){
                        System.out.println(v);
                    }
                });
                System.out.println("num is"+num+"specId"+specId);
                */

                if(num!=null){
                    num = num+quantity;//要校验库存的话再这里校验
                    map.put(specId,num);

                    //System.out.println("null");
                    logger.debug("second if");
                }else{
                    //System.out.println(map);
                    if(logger.isDebugEnabled()){
                        logger.debug(map.toString());
                    }

                    map.put(specId,quantity);//要校验库存的话再这里校验

                    //System.out.println("else");
                    logger.debug("second else");
                }

                //return mapper.writeValueAsString(map);
                json = mapper.writeValueAsString(map);
                //System.out.println(json);
                logger.debug("json: "+json);

            }catch (IOException ex){
                //ex.printStackTrace();

                logger.error(ex.getMessage(),ex);
            }

            //System.out.println("you");
            logger.debug("first if");
        }else{
            map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
            map.put(specId,quantity);//要校验库存的话再这里校验

            try {
                //return mapper.writeValueAsString(map);

                json = mapper.writeValueAsString(map);
            }catch (IOException ex){
                //ex.printStackTrace();

                logger.error(ex.getMessage(),ex);
            }

            //System.out.println("wu");
            logger.debug("first else");
        }

        //return null;

        PairDto<String,Integer,String> pairDto = new PairDto<>();
        pairDto.setFirst(json);

        PairDto<Object,Integer,BigDecimal> amount = amount(map);
        pairDto.setSecond(amount.getSecond());
        pairDto.setThird(amount.getThird().toString());
        return pairDto;
    }

    //这个方法其他类没用到
    @Override
    public Spec getSpec(Integer specId) {
        return specMapper.selectByPrimaryKey(specId);
    }

    @Override
    public int showCartGoodsNumFromRedis(User user) {
        HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
        return hashOperations.size(CART_REDIS_PREFIX+user.getUserId()+"").intValue();
    }

    @Override
    public int showCartGoodsNumFromCookie(String cookieValue) {
        try {
            Map<String, String> map = mapper.readValue(URLDecoder.decode(cookieValue,UTF_8), new TypeReference<HashMap<String, String>>(){});
            return map.size();
        }catch (IOException ex){
            //ex.printStackTrace();

            logger.error(ex.getMessage(),ex);
        }

        return 0;
    }

    //item spec goods
    @Override
    public PairDto<Map<Spec,Integer>,Map<Integer,String>,Object> showSpecWithItem(User user) {
        HashOperations<String,Integer,Integer> hashOperations = redisTemplate.opsForHash();
        Map<Integer,Integer> map = hashOperations.entries(CART_REDIS_PREFIX+user.getUserId()+"");
        //要返回这个A 返回这个是为了 每个规格的数量

        if(map!=null && !map.isEmpty()) {
            List<Integer> list = new ArrayList<>(map.keySet());
            list.add(-1);
            List<Spec> specs = specMapper.selectAllSpecById(list);//要返回这个A
            List<Integer> itemIds = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);

            BigDecimal amount = new BigDecimal(0);

            Map<Spec, Integer> specIntegerMap = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);

            Map<Integer,String> specIdAmountString = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);

            for (Spec spec : specs) {
                itemIds.add(spec.getItemId());

                specIntegerMap.put(spec, map.get(spec.getSpecId()));//两个要返回这个A 换成了这个
                //这里就是每个规格和对应的数量

                //amount = amount.add(new BigDecimal(map.get(spec.getSpecId())).multiply(spec.getRepresentPrice()));//完成计算总价
                //要返回这个

                //每个规格的总价
                specIdAmountString.put(spec.getSpecId(),spec.getRepresentPrice().multiply(new BigDecimal(map.get(spec.getSpecId()))).toString());
            }

            Map<Integer, Item> map1 = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
            List<Item> list1 = itemMapper.selectAllItemById(itemIds);
            for (Item item : list1) {
                map1.put(item.getItemId(), item);
            }

            for (Spec spec : specs) {
                spec.setItem(map1.get(spec.getItemId()));//完成多对一关系
            }

            PairDto<Map<Spec, Integer>, Map<Integer,String>, Object> pairDto = new PairDto<>();
            pairDto.setFirst(specIntegerMap);
            pairDto.setSecond(specIdAmountString);

            return pairDto;
        }else{
            return new PairDto<>();
        }

        //完成了三个任务
        //每个规格及对应的数量
        //每个规格的总价
        //多对一关系 规格-商品
    }


    //这个方法校验库存是直接到数据库的 要改成到redis
    @Override
    public PairDto<Spec,String,String> changeQuantity(User user, Integer specId, Integer quantity) {
        Spec spec = specMapper.selectByPrimaryKey(specId);//要改成从redis校验
        Item item = itemMapper.selectByPrimaryKey(spec.getItemId());
        PairDto<Spec,String,String> pairDto = new PairDto<>();
        if(item.getStatusCode()>=3 || item.getIsDelete()==1){
            pairDto.setThird("invalid");//相当于封装了异常情况
            return pairDto;
        }

        if(spec.getNowInventory()<quantity){
            quantity = spec.getNowInventory();
            pairDto.setThird("shortage");
        }

        HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(CART_REDIS_PREFIX + user.getUserId() + "",specId,quantity);
        pairDto.setFirst(spec);
        pairDto.setSecond(new BigDecimal(quantity).multiply(spec.getRepresentPrice()).toString());
        return pairDto;
    }

    @Override
    public Integer delSpec(User user,Integer specId) {
        HashOperations<String, Integer, Integer> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(CART_REDIS_PREFIX + user.getUserId() + "",specId);

        return hashOperations.size(CART_REDIS_PREFIX + user.getUserId() + "").intValue();
    }

    //这个方法其他类没用到
    @Override
    public Map<Integer,String> checkQuantityBatch(List<Integer> specIds,Map<Integer,Integer> map) {
        List<Spec> specs = specMapper.selectAllSpecById(specIds);//上面还有两处 要不要用缓存
        Map<Integer,String> quantityInfo = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        for(Spec spec:specs){
            if(spec.getNowInventory()<map.get(spec.getSpecId())){
                quantityInfo.put(spec.getSpecId(),"库存不足，最大库存为"+spec.getNowInventory());
            }
        }

        return quantityInfo;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
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
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }
}
