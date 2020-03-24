package com.ywcjxf.mall.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.dao.ItemMapper;
import com.ywcjxf.mall.dao.SpecMapper;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.serviceinterface.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemMapper itemMapper;
    private SpecMapper specMapper;
    private ObjectMapper mapper;
    private RedisTemplate<String,Spec> redisTemplate;
    private RedisTemplate<String,String> redisTemplateString;

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private static final String ITEM_FIRST_SPEC = "item:first:spec:";
    private static final String ITEM_ALL_SPEC = "item:all:spec:";
    private static final String ITEM_TOTAL_SALES = "item:total:sales:";

    @Override
    public Item getItemById(Integer itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        item.setAllSpec(specMapper.selectAllByItemId(itemId));
        List<Item> items = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        try{

            //System.out.println(item.getLikeImageUrl());

            items = mapper.readValue(item.getLikeImageUrl(),new TypeReference<ArrayList<Item>>(){});

            //这个typehandler的源码可以学习
            //new TypeReference<List<Item>>(){}相当于是TypeReference的子类
        }catch (IOException ex){
            //ex.printStackTrace();

            logger.error(item.getLikeImageUrl()+"_"+ex.getMessage(),ex);
        }
        item.setLikeItems(items);
        return item;
    }

    @Override
    public void insertItemImage(Integer itemId, String imageLink) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setImageUrl(imageLink);
        itemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public Spec getItemFirstSpec(Integer itemId) {
        Spec spec = redisTemplate.opsForValue().get(ITEM_FIRST_SPEC+itemId);
        if(spec!=null){
            return spec;
        }
        spec = specMapper.selectFirstSpecByItemId(itemId);
        redisTemplate.opsForValue().set(ITEM_FIRST_SPEC+itemId,spec,3, TimeUnit.DAYS);

        return spec;
    }

    @Override
    public List<Spec> getAllSpecByItemId(Integer itemId) {
        List<Spec> specs = redisTemplate.opsForList().range(ITEM_ALL_SPEC+itemId,0,-1);
        if(specs!=null && !specs.isEmpty()){
            return specs;
        }

        specs = specMapper.selectAllByItemId(itemId);
        if(specs!=null && !specs.isEmpty()){
            redisTemplate.opsForList().leftPushAll(ITEM_ALL_SPEC+itemId,specs);
            redisTemplate.expire(ITEM_ALL_SPEC+itemId,3,TimeUnit.DAYS);
        }
        return specs;
    }

    @Override
    public Integer getTotalSalesByItemId(Integer itemId) {
        String totalSales = redisTemplateString.opsForValue().get(ITEM_TOTAL_SALES+itemId);
        if(totalSales!=null){
            return Integer.parseInt(totalSales);
        }
        Integer totalSalesInteger = itemMapper.selectTotalSalesById(itemId);
        redisTemplateString.opsForValue().set(ITEM_TOTAL_SALES+itemId,totalSalesInteger+"",3,TimeUnit.DAYS);
        return totalSalesInteger;
    }

    @Override
    public Integer getItemIdByNullOrderByIdLimitOne() {
        return itemMapper.selectIdByNullOrderByIdLimitOne();
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Autowired
    public void setSpecMapper(SpecMapper specMapper) {
        this.specMapper = specMapper;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Spec> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setRedisTemplateString(RedisTemplate<String, String> redisTemplateString) {
        this.redisTemplateString = redisTemplateString;
    }
}
