package org.kurenai.rabbitmqdemo;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class Name: RabbitMQConfig
 * Description:
 *
 * @author: liufuhong
 * @mail: liufuhong@ly-sky.com
 * @date: 2019年02月26日
 * @version: 1.0
 */

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue HelloQueue() {
        return new Queue("hello");
    }
}
