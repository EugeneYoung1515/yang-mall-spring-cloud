package com.ywcjxf.mall.zuulserver.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class RedisSessionConfig {

}