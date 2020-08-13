package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 死信队列以及私信交换机配置
 */
@Configuration
public class DlxConfig {

    /**
     * 声明正常的队列（normal_queue_dlx)和交换机(normal_exchange_dlx)
     * 声明死信队列(queue_dlx)和私信交换机(exchange_dlx)
     * 正常队列绑定死刑交换机
     * 设置两个参数
     * x-dead-letter-exchange：死信交换机名称
     * x-dead-letter-routing-key：发送给私信交换机的routingkey
     **/

    //声明所有队列及交换机名称
    public static final String NORMAL_QUEUE = "normal_queue_dlx";
    public static final String NORMAL_EXCHANGE = "normal_exchange_dlx";
    public static final String QUEUE_DLX = "queue_dlx";
    public static final String EXCHANGE_DLX = "exchange_dlx";

    /**
     * 声明正常队列和交换机,并且绑定
     **/
    @Bean("normal_queue_dlx")
    public Queue normalQueueDlx() {
        //正常队列绑定死刑交换机
        //        设置两个参数
        //       x-dead-letter-exchange：死信交换机名称
        //       x-dead-letter-routing-key：发送给私信交换机的routingkey
        //使用map集合传入需要设置的参数
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", EXCHANGE_DLX);
        map.put("x-dead-letter-routing-key", "dlx.error");

        //配置让消息成为死信的参数，同样使用map传入参数
        //设置消息的过期时间
        map.put("x-message-ttl",10*1000);
        //设置消息最大长度，超过此长度消息会成为死信
        map.put("x-max-length",10);

        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(map).build();
    }

    @Bean("normal_exchange_dlx")
    public Exchange normalExchangeDlx() {
        return ExchangeBuilder.topicExchange(NORMAL_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding normalBindingDlx(@Qualifier("normal_queue_dlx") Queue queue, @Qualifier("normal_exchange_dlx") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("normal.dlx.#").noargs();
    }

    /**
     * 配置死信队列以及交换机，并且绑定
     **/
    @Bean("queue_dlx")
    public Queue queueDlx() {
        return QueueBuilder.durable(QUEUE_DLX).build();
    }

    @Bean("exchange_dlx")
    public Exchange exchangeDlx() {
        return ExchangeBuilder.topicExchange(EXCHANGE_DLX).durable(true).build();
    }

    @Bean
    public Binding bindingDlx(@Qualifier("queue_dlx") Queue queue, @Qualifier("exchange_dlx") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dlx.#").noargs();
    }

}
