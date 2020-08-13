package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TtlTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准
     * 队列过期后，会将队列所有消息全部移除
     * 消息过期后，只有消息在队列顶端，才会判断其是否过期
     * @Param
     **/
    @Test
    public void ttlTest(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.error","message ttl");
        }
    }
}
