package com.ywcjxf.mall.service.config;

import com.ywcjxf.mall.service.OrderServiceImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.OrderAndPayConstants.*;

@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue orderQueue(){
        //return new Queue("order");

        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("x-dead-letter-exchange",RABBITMQ_DEAD_LETTER_EXCHANGE);
        return new Queue(RABBITMQ_ORDER,true,false,false,map);
    }

    @Bean
    public Exchange deadExchange(){
        return new DirectExchange(RABBITMQ_DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue deadQueue(){
        return new Queue(RABBITMQ_DEAD_QUEUE);
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(RABBITMQ_ORDER).noargs();
    }

    @Bean
    public Queue queueForIncrInventory(){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("x-dead-letter-exchange",RABBITMQ_DEAD_LETTER_EXCHANGE);
        map.put("x-message-ttl",TEN_MIN_AS_MILLI_SECONDS);//600000
        return new Queue(RABBITMQ_INCR_INVENTORY,true,false,false,map);
    }

    @Bean
    public Queue deadQueueForIncrInventory(){
        return new Queue(RABBITMQ_DEAD_QUEUE_INCR_INVENTORY);
    }

    @Bean
    public Binding deadBindingForIncrInventory(){
        return BindingBuilder.bind(deadQueueForIncrInventory()).to(deadExchange()).with(RABBITMQ_INCR_INVENTORY).noargs();
    }

    @Bean
    public Queue queueForIncrInventory30min(){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("x-dead-letter-exchange",RABBITMQ_DEAD_QUEUE);
        map.put("x-message-ttl",THIRTY_MIN_AS_MILLI_SECONDS);
        return new Queue(RABBITMQ_INCR_INVENTORY_30_MIN,true,false,false,map);
    }

    @Bean
    public Queue deadQueueForIncrInventory30min(){
        return new Queue(RABBITMQ_DEAD_QUEUE_INCR_INVENTORY_30_MIN);
    }

    @Bean
    public Binding deadBindingForIncrInventory30min(){
        return BindingBuilder.bind(deadQueueForIncrInventory30min()).to(deadExchange()).with(RABBITMQ_INCR_INVENTORY_30_MIN).noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,OrderServiceImpl orderService){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(orderService);
        rabbitTemplate.setReturnCallback(orderService);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    /*
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setAutoStartup(true);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(10);
        return factory;
    }
    */

}