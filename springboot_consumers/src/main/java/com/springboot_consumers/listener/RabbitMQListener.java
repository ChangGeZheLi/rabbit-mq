package com.springboot_consumers.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 队列监听器
 */
@Component
public class RabbitMQListener {

    /**
     * @Description 接收到的消息会自动封装到message对象中
     * 可以通过message的方法取出对应的数据
     **/
    @RabbitListener(queues = "springboot_queue")
    public void ListenerQueue(Message message){
        System.out.println(new String(message.getBody()));
    }
}
