package com.ywcjxf.mall.web.config;

import com.ywcjxf.mall.web.filter.RedisSessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisSessionConfig {

    @Bean
    public FilterRegistrationBean redisSessionFilter2(){
        FilterRegistrationBean redisSessionFilter = new FilterRegistrationBean();
        redisSessionFilter.setFilter(redisSessionFilter());
        redisSessionFilter.addUrlPatterns("/*");
        redisSessionFilter.setOrder(Integer.MAX_VALUE-1);
        return redisSessionFilter;
    }

    //不用上面一个 单用下面一个也行
    @Bean
    public RedisSessionFilter redisSessionFilter(){
        return new RedisSessionFilter();
    }

}
