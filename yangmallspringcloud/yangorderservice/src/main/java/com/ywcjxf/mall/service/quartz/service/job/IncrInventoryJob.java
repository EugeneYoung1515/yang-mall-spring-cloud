package com.ywcjxf.mall.service.quartz.service.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywcjxf.mall.service.OrderServiceImpl;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.SimpleCartDto;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class IncrInventoryJob implements Job {
    private OrderServiceImpl orderService;
    private ObjectMapper objectMapper;

    public static final Logger logger = LoggerFactory.getLogger(IncrInventoryJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            SimpleCartDto simpleCartDto = objectMapper.readValue(jobDataMap.getString("simpleCartDto"),SimpleCartDto.class);
            orderService.receiveAndIncrInventory(simpleCartDto,jobDataMap.getString("pay"));

            logger.info("run");
            if(logger.isDebugEnabled()){
                logger.debug(simpleCartDto.getMap().toString());
                logger.debug(jobDataMap.getString("pay"));
            }

        }catch (IOException ex){
            logger.error("IncrInventoryJob execute"+"_"+ex.getMessage(),ex);
        }
    }


    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
