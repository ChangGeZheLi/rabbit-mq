package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.ExhaustedRetryException;

/**
 * @Description: 测试消息producer到exchange的可靠性
 */
@Configuration
public class RabbitMQConfirm{

    /**
     * @Description 定义交换机和队列名称
     **/
    public static final String EXCHANGE_NAME = "springboot_exchange_confirm";
    public static final String QUEUE_NAME = "springboot_queue_confirm";

    /**
     * @Description 创建队列
     **/
    @Bean("queueConfirm")
    public Queue queueConfirm(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * @Description 创建交换机
     **/
    @Bean("exchangeConfirm")
    public Exchange exchangeConfirm(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * @Description 绑定队列和交换机
     **/
    @Bean
    public Binding bindConfirm(@Qualifier("queueConfirm") Queue queue, @Qualifier("exchangeConfirm") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("confirm").noargs();
    }

}
