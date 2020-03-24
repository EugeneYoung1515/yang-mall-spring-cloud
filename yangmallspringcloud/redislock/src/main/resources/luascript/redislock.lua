--redis.call('set',KEYS[1],ARGV[1],'PX',ARGV[2],'NX')

--local value = false
if redis.call('exists',KEYS[1])==0 then
    return redis.call('setex',KEYS[1],ARGV[2],ARGV[1]) --and true
end
--return value
