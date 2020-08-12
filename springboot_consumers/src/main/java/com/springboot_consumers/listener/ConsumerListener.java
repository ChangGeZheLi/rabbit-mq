package com.springboot_consumers.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 消费端消息确认监听器
 */
@Component
public class ConsumerListener {

    /**
     *
     * @Param message 队列中的消息
     * channel 当前的消息队列
     * tag 取出来当前消息在队列中的索引
     * 用@Header(AmqpHeaders.DELIVERY_TAG)注解拿到
     **/
//    @RabbitListener(queues = "springboot_queue_confirm")
//    public void ackListener(Message message, Channel channel , @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
//
//        try {
//
//            Thread.sleep(1000);
//
//            //打印消息
//            System.out.println(new String(message.getBody()));
//            //处理业务逻辑
//            System.out.println("处理业务逻辑===================");
//            //手动签收
//            channel.basicAck(tag,true);
//        }catch (Exception e){
//            /**
//             *第三个参数：requeue：重回队列，如果设置为true，则消息重回到queue，broker会重新发送消息
//             **/
//            channel.basicNack(tag,true,true);
//        }
//    }

    /**
     * Spring 实现 Consumer ACK机制
     *  设置手动签收：acknowledge=“manual”
     *  让监听器实现ChannelAwareMessageListener接口
     *  如果消息成功处理，调用channel的basicAck签收
     *  如果消息处理失败，则调用channel的basicNack拒绝签收，broker重新发送给consumer
     * @Param [message, channel]
     **/

}
