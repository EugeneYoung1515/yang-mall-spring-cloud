package com.ywcjxf.mall.service.util;

import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;

@Component
public class RedisLock {
    private RedisTemplate<String,String> redisTemplate;
    //private RedisScript<List> redisScript;

    private RedisScript<Boolean> redisScript;
    //private RedisScript<List> releaseLockScript;

    private RedisScript<Long> releaseLockScript;

    public PairDto<Boolean,String,Object> acquireLockWithTimeout(String lockName){
        String identifier = UUID.randomUUID().toString();
        lockName = "lock:"+lockName;
        int lockTimeout = 1000;//毫秒

        int acquireTimeout = 1000;

        boolean acquire = false;
        long end = System.currentTimeMillis()+acquireTimeout;

        int acquireInt = 1;

        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(lockName);

        while (System.currentTimeMillis()<end && !acquire){
            //List list1 = redisTemplate.execute(redisScript,list,identifier,lockTimeout/1000);
            //System.out.println(list1);
            //Boolean temp = (Boolean)list1.get(0);

            Boolean temp = redisTemplate.execute(redisScript,list,identifier,lockTimeout/1000);

            //System.out.println(temp);
            if(temp!=null) {
                acquire = temp;
                acquireInt = acquire ? 0 : 1;
            }
            try {
                Thread.sleep(3 * acquireInt);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        PairDto<Boolean,String,Object> pairDto = new PairDto<>();
        pairDto.setFirst(acquire);

        //System.out.println(acquire);

        pairDto.setSecond(identifier);

        /*
        try{
            Thread.sleep(30000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        */

        return pairDto;
    }

    public boolean releaseLock(String lockName,String identifier){
        lockName="lock:"+lockName;
        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(lockName);

        //System.out.println(redisTemplate.execute(releaseLockScript,list,identifier).get(0));

        //System.out.println(redisTemplate.execute(releaseLockScript,list,identifier));

        redisTemplate.execute(releaseLockScript,list,identifier);

        return true;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
    @Autowired
    @Qualifier("redisLockScript")
    public void setRedisScript(RedisScript<List> redisScript) {
        this.redisScript = redisScript;
    }
    */

    @Autowired
    @Qualifier("redisLockScript")
    public void setRedisScript(RedisScript<Boolean> redisScript) {
        this.redisScript = redisScript;
    }

    /*
    @Autowired
    @Qualifier("releaseLockScript")
    public void setReleaseLockScript(RedisScript<List> releaseLockScript) {
        this.releaseLockScript = releaseLockScript;
    }
    */

    @Autowired
    @Qualifier("releaseLockScript")
    public void setReleaseLockScript(RedisScript<Long> releaseLockScript) {
        this.releaseLockScript = releaseLockScript;
    }
}
