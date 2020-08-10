package com.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 生产者角色
 */
public class Producer_Routing {

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

        /**
         exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
         参数;
             exchange:交换机名称
             type：枚举类型
                 DIRECT("direct")：定向
                 FANOUT("fanout")：广播，发送消息到每一个与之绑定的队列
                 TOPIC("topic")：通配符类型
                 HEADERS("headers")：参数匹配
             durable：是否持久化
             autoDelete：是否自动删除
             internal：内部使用，一般false
             argument：参数，一般为null
         **/

        //设置交换机和队列的名字
        String exchangeName = "test_direct";
        String queueName1 = "test_direct_queue1";
        String queueName2 = "test_direct_queue2";

        //创建交换机 
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        //创建队列
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);

        /**
         queueBind(String queue, String exchange, String routingKey)
         参数：
            queue：队列名称
            exchange：交换机名称
            routingKey：路由键，即绑定规则
                如果交换机得类型为FANOUT，routingKey设置为""
         **/
        //队列1绑定error路由键
        channel.queueBind(queueName1,exchangeName,"error");
        //队列2绑定info error warning路由键
        channel.queueBind(queueName2,exchangeName,"info");
        channel.queueBind(queueName2,exchangeName,"error");
        channel.queueBind(queueName2,exchangeName,"warning");

        //设置发送的数据并且发送
        String body = "日志信息：李四调用了findAll方法...日志级别：info...";
        channel.basicPublish(exchangeName,"info",null,body.getBytes());

        //释放资源
        channel.close();
        connection.close();
    }
}
