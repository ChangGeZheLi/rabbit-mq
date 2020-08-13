package com.springboot_consumers.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息成为死信的第三种请款，消费端拒绝接收
 */
@Component
public class DlxListener {

    /**
     *
     * @Param message 队列中的消息
     * channel 当前的消息队列
     * tag 取出来当前消息在队列中的索引
     * 用@Header(AmqpHeaders.DELIVERY_TAG)注解拿到
     **/
    @RabbitListener(queues = "normal_exchange_dlx")
    public void ackListener(Message message, Channel channel , @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {

        try {

            Thread.sleep(1000);

            //打印消息
            System.out.println(new String(message.getBody()));
            //处理业务逻辑
            System.out.println("处理业务逻辑===================");
            //出现异常
//            int i = 2/0;
            //手动签收
            channel.basicAck(tag,true);
        }catch (Exception e){
            /**
             * 第三个参数：requeue：重回队列，如果设置为true，则消息重回到queue，broker会重新发送消息
             * 为了模拟消息成为 死信，所以不重回队列
             **/
            System.out.println("出现异常，拒绝接收");

            channel.basicNack(tag,true,false);
        }
    }

}
