package com;

import com.rabbitmq.config.RabbitMQConfirm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 测试producer的消息可靠性
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerConfirmTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式
     *   开启确认模式：需要在application.xml中开启publisher-confirm=“true”
     *   在rabbitTemplate定义ConfirmCalBack回调函数
     **/
    @Test
    public void testConfirm(){

        /**
         * 定义回调函数
         **/
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 
             * @Param correlationData 相关的配置信息
             * ack exchange交换机是否成功收到了消息
             * cause 如果没有成功收到消息，cause会存储失败的原因
             * @return void
             **/
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("Producer 发送数据到 Exchange成功");
                if (ack){
                    //成功就打印消息
                }else {
                    //不成功就进行处理，让消息成功发送
                }
            }
        });

        rabbitTemplate.convertAndSend(RabbitMQConfirm.EXCHANGE_NAME,"confirm","ACK confirmed");
    }

    /**
     * return模式，当消息发送给exchange后，exchange路由到Queue失败时才会执行ReturnCallback
     *      开启回退模式：在配置文件中publisher-returns: true
     *      设置ReturnCallback回调函数
     *      设置Exchange处理消息的模式
     *          如果消息没有路由到queue，则丢弃消息（默认）
     *          如果消息没有路由到Queue，返回给消息发送方ReturnCallback
     * @Param []
     * @return void
     **/
    @Test
    public void testReturn(){

        /**
         * 设置交换机处理失败消息的模式
         * 默认模式下，会丢弃消息，就不会出现失败进入到回调函数
         * 所以需要设置交换机处理模式将各种信息返回到回调函数中
         * @Param []
         * @return void
         **/
        rabbitTemplate.setMandatory(true);

        /**
         * 定义回调函数
         **/
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @Param message 返回的消息对象
             * replyCode 错误码
             * replyText 错误信息
             * exchange 交换机名称
             * routingKey 路由键
             **/
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 返回数据了");
                //可以操作参数来获得对应的信息
                //进行处理，重新发送消息到queue
            }
        });

        rabbitTemplate.convertAndSend(RabbitMQConfirm.EXCHANGE_NAME,"confirm","ACK confirmed");
    }
}
