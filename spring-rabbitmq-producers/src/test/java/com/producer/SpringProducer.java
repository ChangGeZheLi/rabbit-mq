package com.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class SpringProducer {

    //注入RabbitTemplate
    @Autowired
    private RabbitTemplate template;

    @Test
    public void test1(){
        //使用spring提供的模板对象发送消息，大大简化了代码量
        template.convertAndSend("spring_queue","hello spring rabbitmq");
    }

    /**
     * @Description 发送消息到fanout交换机上
     * @Param []
     * @return void
     **/
    @Test
    public void test2(){
        //使用spring提供的模板对象发送消息，大大简化了代码量
        template.convertAndSend("spring_fanout_exchange","","hello spring fanout...");
    }

    /**
     * @Description 发送消息到topics交换机上
     * @Param []
     * @return void
     **/
    @Test
    public void test3(){
        //使用spring提供的模板对象发送消息，大大简化了代码量
        template.convertAndSend("spring_topic_exchange","error.mysql.warning","hello spring topic...");
    }
}
