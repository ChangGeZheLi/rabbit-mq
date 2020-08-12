package com.springboot_consumers.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 消费端限流监听器
 */
@Component
public class QosListener {

    /**
     * 消费端限流机制
     *      确保ack机制为手动确认
     *      配置文件中配置prefetch=1，表实消费端每次从mq拉取一条消息来消费，只有 手动确认消费完毕后
     * 才去拉取下一条消息
     **/
    @RabbitListener(queues = "springboot_queue_confirm")
    public void qosListener(Message message, Channel channel) throws Exception {

        Thread.sleep(2000);
        //打印消息
        System.out.println(new String(message.getBody()));

        //获取tag
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        //手动确认
        channel.basicAck(deliveryTag,true);
    }

}
