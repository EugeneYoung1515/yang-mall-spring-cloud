package com.ywcjxf.mall.service.config;

import com.ywcjxf.mall.service.quartz.annotation.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    /*
    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix="quartz.datasource")
    public DataSource quartzDataSource(){
        return DataSourceBuilder.create().build();
    }
    */

    @Bean
    @org.springframework.boot.autoconfigure.quartz.QuartzDataSource
    @ConfigurationProperties(prefix="quartz.datasource")
    public DataSource quartzDataSource(){
        return DataSourceBuilder.create().build();
    }


}
