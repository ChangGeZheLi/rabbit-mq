package com.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 消费者角色
 */
public class Consumer_PubSub2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置参数
        factory.setHost("192.168.48.130");
        factory.setPort(5672);
        factory.setVirtualHost("/sy");
        factory.setUsername("root");
        factory.setPassword("root");
        //创建连接
        Connection connection = factory.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();

        //设置交换机名称
        String queueName1 = "test_fanout_queue1";
        String queueName2 = "test_fanout_queue2";

         /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck：是否自动确认
            3. callback：回调对象
         */
        //接收消息
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel){
            /**
             下面是一个回调函数：也就是当收到消息时，会自动执行该方法

             consumerTag:消费者标识
             envelope:可以通过该对象，获取一些信息，交换机，路由key。。。
             properties:配置信息
             body:生产者发送到数据信息
             **/
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                System.out.println("consumerTag "+consumerTag);
//                System.out.println("Exchange "+envelope.getExchange());
//                System.out.println("RoutingKey "+envelope.getRoutingKey());
//                System.out.println("properties "+properties);
                System.out.println("body "+new String(body));
                System.out.println("将日志信息存储到数据库===================");
            }
        };
        channel.basicConsume(queueName2,true,consumer );

        //consumer不需要关闭资源，因为需要时刻监听发送的数据，关闭则无法实时监听

    }
}
