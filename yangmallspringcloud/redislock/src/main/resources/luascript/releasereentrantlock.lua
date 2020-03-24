if redis.call('hincrby',KEYS[1],KEYS[4],-1)==0 then
    return redis.call('del',KEYS[1]) --or true
end
