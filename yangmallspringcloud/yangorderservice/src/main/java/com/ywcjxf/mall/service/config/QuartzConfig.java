package com.ywcjxf.mall.service.config;

import com.ywcjxf.mall.service.quartz.AutowiringSpringBeanJobFactory;
import com.ywcjxf.mall.service.quartz.annotation.QuartzDataSource;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    /*
    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory jobFactory, @QuartzDataSource DataSource dataSource)  throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);

        factory.setStartupDelay(10);
        factory.setAutoStartup(true);

        factory.setWaitForJobsToCompleteOnShutdown(true);

        factory.setOverwriteExistingJobs(true);

        factory.setDataSource(dataSource);

        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);

        factory.setTransactionManager(platformTransactionManager);
        factory.setQuartzProperties(quartzProperties());

        //上面几个是setter 顺序调换了 不要紧

        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
    */

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(){
        return factory -> {
            factory.setStartupDelay(10);
            factory.setAutoStartup(true);

            factory.setWaitForJobsToCompleteOnShutdown(true);

            factory.setOverwriteExistingJobs(true);
        };
    }

}
