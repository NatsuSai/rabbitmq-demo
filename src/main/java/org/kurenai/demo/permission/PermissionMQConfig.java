package org.kurenai.demo.permission;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liufuhong
 * @since 2019-06-10 17:35
 */

@Configuration
@EnableConfigurationProperties(PermissionMQProperties.class)
@Slf4j
public class PermissionMQConfig {

  public static final String PERMISSION_INIT_QUEUE = "permission.init.queue.test";
  public static final String PERMISSION_INIT_FAILED_QUEUE = "permission.init.failed.queue";
  public static final String PERMISSION_INIT_DELAY_QUEUE = "permission.init.delay.queue.test";
  public static final String PERMISSION_EXCHANGE = "permission.exchange";
  public static final String PERMISSION_INIT_ROUTING_KEY = "permission.init.test";
  public static final String PERMISSION_INIT_FAILED_ROUTING_KEY = "permission.init.failed";
  public static final String PERMISSION_INIT_DELAY_ROUTING_KEY = "permission.init.delay.test";

  private final PermissionMQProperties properties;

  public PermissionMQConfig(PermissionMQProperties properties) {
    this.properties = properties;
  }

  @Bean
  public Queue permissionInitQueue() {
    return new Queue(PERMISSION_INIT_QUEUE, true);
  }

  @Bean
  public Queue permissionInitFailedQueue() {
    return new Queue(PERMISSION_INIT_FAILED_QUEUE, true);
  }

  @Bean
  public Queue permissionInitDelayQueue() {
    Map<String, Object> params = new HashMap<>();
    params.put("x-message-ttl", properties.getIntervals());
    params.put("x-dead-letter-exchange", PERMISSION_EXCHANGE);
    params.put("x-dead-letter-routing-key", PERMISSION_INIT_ROUTING_KEY);
    return new Queue(PERMISSION_INIT_DELAY_QUEUE, true, false, false, params);
  }

  @Bean
  public TopicExchange permissionExchange() {
    return new TopicExchange(PERMISSION_EXCHANGE);
  }

  @Bean
  public Binding permissionInitBinding() {
    return BindingBuilder.bind(permissionInitQueue()).to(permissionExchange()).with(PERMISSION_INIT_ROUTING_KEY);
  }

  @Bean
  public Binding permissionInitFailedBinding() {
    return BindingBuilder.bind(permissionInitFailedQueue()).to(permissionExchange()).with(PERMISSION_INIT_FAILED_ROUTING_KEY);
  }

  @Bean
  public Binding permissionInitDelayBinding() {
    return BindingBuilder.bind(permissionInitDelayQueue()).to(permissionExchange()).with(PERMISSION_INIT_DELAY_ROUTING_KEY);
  }
}