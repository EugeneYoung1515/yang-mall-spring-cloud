package com.ywcjxf.mall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ywcjxf.mall.dao.ItemMapper;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.serviceinterface.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;

@Service
public class SearchServiceImpl implements SearchService {
    private ItemMapper itemMapper;
    private RedisTemplate<String,String> redisTemplate;
    //springboot 1.5时这里注入的是自己配置RedisTemplate<String,T>
    //springboot 2时这里注入的是StringRedisTemplate
    //在RedisConfig那里配置了一个StringRedisTemplate 覆盖了默认的

    private RedisScript<List> redisScript;

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    private static final String HOT_WORDS = "hotwords:";

    @Override
    public Page<Item> search(String keyword, String sort, Integer pageNum, Integer pageSize) {
        redisTemplate.opsForZSet().incrementScore("hotwords:",keyword,-1);

        PageHelper.startPage(pageNum,pageSize);
        if(sort==null){
            return itemMapper.selectByFullTextSearch(keyword);
        }
        return itemMapper.selectByFullTextSearchWithSort(keyword,sort);
    }

    @Override
    public Integer searchCount(String keyword) {
        return itemMapper.selectCountByFullTextSearch(keyword);
    }

    @Override
    public Set<String> hotWords() {
        return redisTemplate.opsForZSet().range(HOT_WORDS,0,9);
    }

    @Scheduled(fixedRate = 90000,initialDelay = 1000)
    //@Scheduled(cron = "0 0 0 */7 * *")
    public void rescaleViewed(){
        redisTemplate.opsForZSet().removeRange(HOT_WORDS,200,-1);
        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(HOT_WORDS);
        redisTemplate.execute(redisScript,list,0.5);
        //System.out.println("rescale");
        logger.info("rescale");
    }


    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }


    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    @Qualifier("script")
    public void setRedisScript(RedisScript<List> redisScript) {
        this.redisScript = redisScript;
    }
}
