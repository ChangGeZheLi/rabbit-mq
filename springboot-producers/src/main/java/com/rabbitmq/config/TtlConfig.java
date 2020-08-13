package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 配置具有过期时间的队列以及交换机
 */
@Configuration
public class TtlConfig {

    /**
     * 设置过期时间的队列
     * 过期时间参数应该使用x-expires来设置，否则会出现问题
     * 而且该参数设置之后，如果数据过期则队列也会被删除
     * @Param
     **/

    @Bean("ttlQueue")
    public Queue ttlQueue(){
        //设置参数集合map
        Map<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",10*1000);

        return QueueBuilder.durable("test_queue_ttl").withArguments(map).build();
    }

    /**
     * 配置有过期时间的交换机
     * @Param
     **/
    @Bean("ttlExchange")
    public Exchange ttlExchange(){
        return ExchangeBuilder.topicExchange("test_exchange_ttl").durable(true).build();
    }

    /**
     * 绑定交换机以及队列
     * @Param
     **/
    @Bean
    public Binding ttlQueueExchange(@Qualifier("ttlQueue") Queue queue, @Qualifier("ttlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();
    }
}
