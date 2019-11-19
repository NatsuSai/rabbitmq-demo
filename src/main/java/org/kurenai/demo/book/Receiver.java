package org.kurenai.demo.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Class Name: Receiver
 * Description:
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2019年02月26日
 * @version: 1.0
 */

@Component
@RabbitListener(queues = "hello")
@Slf4j
public class Receiver {

    @RabbitHandler
    public void process(String hello) {
        log.info("Receiver : " + hello);
    }

}
