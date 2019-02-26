package org.kurenai.rabbitmqdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Class Name: Sender
 * Description:
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2019年02月26日
 * @version: 1.0
 */

@Slf4j
@Component
public class Sender {
    private AmqpTemplate rabbitTemplate;

    @Autowired
    public Sender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
        String context = "hello " + new Date();
        log.info(context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
