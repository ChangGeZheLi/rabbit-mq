package com.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 生产者角色
 */
public class Producer_WorkQueues {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置参数
        factory.setHost("192.168.48.130");
        factory.setPort(5672);
        factory.setVirtualHost("/sy");
        factory.setUsername("root");
        factory.setPassword("root");
        //创建连接Connection
        Connection connection = factory.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //简单模式无需交换机，所以直接创建队列
        /*
          queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
          参数：
            queue：队列名称
            durable：是否持久化
            exclusive：
                是否独占，只能有一个消费者监听队列
                当连接关闭时，是否解除队列
            autoDelete：是否自动删除
            argument：参数，一般输入空
         **/
        //如果没有该名称的队列，会创建一个，如果有则不会
        channel.queueDeclare("workQueues",true,false,false,null);
        //发送消息
        /*
         basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         参数：
            exchange：交换机名称，简单模式下不使用交换机
            routingKey：路由名称
            props：配置信息
            body：发送的消息数据
         * */
        //发送多条消息
        for (int i = 0; i < 10; i++) {
            String body = i+"  hello rabbitmq~~~";
            channel.basicPublish("","workQueues",null,body.getBytes());
        }
        //释放资源
        channel.close();
        connection.close();
    }
}
