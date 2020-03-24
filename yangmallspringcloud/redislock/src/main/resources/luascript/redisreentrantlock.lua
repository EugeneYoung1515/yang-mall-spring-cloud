if redis.call('exists',KEYS[1])==0 then
    local flag = redis.call('hset',KEYS[1],KEYS[4],1) --and true
    redis.call('expire',KEYS[1],ARGV[1])
    --local result = {}
    --result[1] = "\"qaz\""
    --result[2] = flag
    --return "\"qaz\"",flag
    --return result
    return "\""..flag.."\""
else

    local hashkeys = redis.call('hkeys',KEYS[1])
    local inputstr = hashkeys[1]

    local t={}
    local sep=":"
    for str in string.gmatch(inputstr, "([^"..sep.."]+)") do
        table.insert(t, str)
    end

    if t[2]==KEYS[3] then
        redis.call('hincrby',KEYS[1],inputstr,1)
        --local result = {}
        --result[1] = "\""..t[1].."\""
        --result[2] = 1
        --return t[1],1
        --return result
        return "\""..t[1].."\""
    end
end
