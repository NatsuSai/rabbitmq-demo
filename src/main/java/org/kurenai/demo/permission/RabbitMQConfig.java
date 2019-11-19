package org.kurenai.demo.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AutoConfigureAfter(RabbitAutoConfiguration.class)
public class RabbitMQConfig {

  public RabbitMQConfig(SimpleRabbitListenerContainerFactory factory) {
    //listener 添加转换器
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
  }

  @Bean
  public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
    connectionFactory.setPublisherConfirms(true);
    connectionFactory.setPublisherReturns(true);
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.trace("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
    rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }

}