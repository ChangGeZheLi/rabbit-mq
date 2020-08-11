package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @Description: springboot的rabbitmq配置类
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "springboot_topic_exchange";
    public static final String QUEUE_NAME = "springboot_queue";

    /**
     * @Description 交换机配置类
     * @Param
     * @return
     **/
    @Bean("springbootExchange")
    public Exchange springbootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * @Description 队列配置
     * @Param
     * @return
     **/
    @Bean("springbootQueue")
    public Queue springbootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * @Description 队列和交换机绑定关系
     * 需要配置交换机名称，队列名称，以及路由键
     * 使用注解Qualifier指定传入的交换机和队列名称
     * @Param
     * @return
     **/
    @Bean
    public Binding bindQueueExchange(@Qualifier("springbootExchange") Exchange exchange,@Qualifier("springbootQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

}
