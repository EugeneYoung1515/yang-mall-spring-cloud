package com.ywcjxf.mall.service.quartz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.service.quartz.service.job.IncrInventoryJob;
import com.ywcjxf.mall.serviceinterface.dto.SimpleCartDto;
import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static com.ywcjxf.mall.service.constant.OrderAndPayConstants.*;

@Component
public class QuartzScheduleService {

    private Scheduler scheduler;
    private ObjectMapper objectMapper;

    public static final Logger logger= LoggerFactory.getLogger(QuartzScheduleService.class);

    public QuartzScheduleService(Scheduler scheduler, ObjectMapper objectMapper) {
        this.scheduler = scheduler;
        this.objectMapper = objectMapper;
    }

    public void schedule(SimpleCartDto simpleCartDto,int minute){
        String pay = null;

        int milliSeconds = 0;

        if(minute==TEN_MIN){
            pay = EMPTY_STRING_PREFIX;
            milliSeconds = TEN_MIN_AS_MILLI_SECONDS;
        }
        if(minute==THIRTY_MIN){
            pay = PAY;
            milliSeconds = THIRTY_MIN_AS_MILLI_SECONDS;
        }

        try {
            String json = objectMapper.writeValueAsString(simpleCartDto);

            JobDetail jobDetail = JobBuilder.newJob(IncrInventoryJob.class).usingJobData("simpleCartDto", json).usingJobData("pay", pay).storeDurably().build();
            scheduler.addJob(jobDetail,true);

            Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).startAt(new Date(System.currentTimeMillis()+milliSeconds)).build();

            scheduler.scheduleJob(trigger);
        }catch (IOException|SchedulerException ex){
            logger.error("QuartzScheduleService schedule"+"_"+ex.getMessage(),ex);
        }
    }
}
