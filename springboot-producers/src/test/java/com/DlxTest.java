package com;

import com.rabbitmq.config.DlxConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 死信队列及死信模拟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DlxTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送测试死信消息
     * 过期时间
     * 长度限制
     * 消息拒收
     **/
    @Test
    public void test(){
        //测试过期时间
        //rabbitTemplate.convertAndSend(DlxConfig.NORMAL_EXCHANGE,"normal.dlx.message","i am a dead letter!");

        //测试长度
        for (int i = 0; i < 15; i++) {
            rabbitTemplate.convertAndSend(DlxConfig.NORMAL_EXCHANGE,"normal.dlx.message","i am a dead letter!");
        }
    }
}
