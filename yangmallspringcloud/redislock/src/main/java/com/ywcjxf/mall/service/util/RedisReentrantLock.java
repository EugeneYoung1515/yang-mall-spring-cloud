package com.ywcjxf.mall.service.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;

@Component
public class RedisReentrantLock {
    private RedisTemplate<String,String> redisTemplate;

    private RedisScript<String> redisScript;

    private RedisScript<Long> releaseLockScript;

    public LockEntity acquireLockWithTimeout(String lockName){
        long threadId = Thread.currentThread().getId();

        String identifier = UUID.randomUUID().toString();
        lockName = "lock:"+lockName;
        int lockTimeout = 20000;//毫秒

        int acquireTimeout = 1000;

        boolean acquire = false;
        long end = System.currentTimeMillis()+acquireTimeout;

        int acquireInt = 1;

        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(lockName);
        list.add(identifier);
        list.add(threadId+"");
        list.add(identifier+":"+threadId);

        while (System.currentTimeMillis()<end && !acquire){

            String temp = redisTemplate.execute(redisScript,list,lockTimeout/1000);

            if(temp!=null&&temp.equals("1")){
                acquire=true;
                acquireInt=0;
            }
            if(temp!=null&&!temp.equals("1")){
                acquire = true;
                acquireInt = 0;
                identifier = temp;
            }
            try {
                Thread.sleep(2 * acquireInt);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }

        return new LockEntity(acquire,lockName,identifier,threadId,this);
    }

    private boolean releaseLock(String lockName,String identifier){
        long threadId = Thread.currentThread().getId();

        //lockName="lock:"+lockName;
        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(lockName);
        list.add(identifier);
        list.add(threadId+"");
        list.add(identifier+":"+threadId);

        redisTemplate.execute(releaseLockScript,list,identifier);

        return true;
    }


    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    @Qualifier("redisReentrantLockScript")
    public void setRedisScript(RedisScript<String> redisScript) {
        this.redisScript = redisScript;
    }

    @Autowired
    @Qualifier("releaseReentrantLockScript")
    public void setReleaseLockScript(RedisScript<Long> releaseLockScript) {
        this.releaseLockScript = releaseLockScript;
    }

    public class LockEntity{
        private boolean acquire;

        private String lockName;
        private String uuid;
        private long threadId;

        private RedisReentrantLock redisReentrantLock;

        public LockEntity(boolean acquire, String lockName, String uuid, long threadId, RedisReentrantLock redisReentrantLock) {
            this.acquire = acquire;
            this.lockName = lockName;
            this.uuid = uuid;
            this.threadId = threadId;
            this.redisReentrantLock = redisReentrantLock;
        }

        public void releaseLock(){
            redisReentrantLock.releaseLock(lockName,uuid);
        }

        public String getLockName() {
            return lockName;
        }

        public void setLockName(String lockName) {
            this.lockName = lockName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public long getThreadId() {
            return threadId;
        }

        public void setThreadId(long threadId) {
            this.threadId = threadId;
        }

        public void setRedisReentrantLock(RedisReentrantLock redisReentrantLock) {
            this.redisReentrantLock = redisReentrantLock;
        }

        public boolean isAcquire() {
            return acquire;
        }

        public void setAcquire(boolean acquire) {
            this.acquire = acquire;
        }
    }
}
