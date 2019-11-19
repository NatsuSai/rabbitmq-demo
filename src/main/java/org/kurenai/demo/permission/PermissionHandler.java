package org.kurenai.demo.permission;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 权限MQ处理器
 *
 * @author liufuhong
 * @since 2019-06-12 14:19
 */
@Component
@EnableConfigurationProperties(PermissionMQProperties.class)
@Slf4j
public class PermissionHandler {

  private final RabbitTemplate          rabbitTemplate;
  private final PermissionMQProperties  properties;

  public PermissionHandler(RabbitTemplate rabbitTemplate,
                           PermissionMQProperties properties) {
    this.rabbitTemplate = rabbitTemplate;
    this.properties = properties;
  }

  @RabbitListener(queues = PermissionMQConfig.PERMISSION_INIT_QUEUE)
  @Transactional(rollbackFor = Exception.class)
  public void handleInitPermission(PermissionQueueMsg permissionQueueMsg, Message message, Channel channel,
                                   @Header(required = false, name = "x-death") List<Map<String, Object>> xDeath) throws IOException {
    final long    deliveryTag = message.getMessageProperties().getDeliveryTag();
    ReentrantLock lock        = new ReentrantLock();
    try {
      log.debug("[initPermission 监听的消息] - [{}]", permissionQueueMsg);
      long count = Optional.ofNullable(xDeath)
                           .flatMap(x -> x.stream().findFirst())
                           .map(d -> (long) d.get("count"))
                           .orElse(0L);
      if (count <= properties.getMaxAttempts()) {
        log.debug("retry times: {}", count);
        persistentPermission(permissionQueueMsg);
      } else {
        log.warn("[{}] retry over {} times, send message to [{}] and throw away!",
            permissionQueueMsg.getAppName() + "@" + permissionQueueMsg.getVersion(),
            properties.getMaxAttempts(),
            PermissionMQConfig.PERMISSION_INIT_FAILED_QUEUE);
        rabbitTemplate.convertAndSend(
            PermissionMQConfig.PERMISSION_EXCHANGE,
            PermissionMQConfig.PERMISSION_INIT_FAILED_ROUTING_KEY,
            message);
      }
    } catch (Exception e) {
      // 发送至重试队列
      log.warn("retry!");
      rabbitTemplate.convertAndSend(PermissionMQConfig.PERMISSION_EXCHANGE,
          PermissionMQConfig.PERMISSION_INIT_DELAY_ROUTING_KEY,
          message,
          msg -> {
            msg.getMessageProperties().setExpiration(properties.getIntervals() + "");
            return msg;
          });
      throw e;
    } finally {
      channel.basicAck(deliveryTag, false);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public void persistentPermission(PermissionQueueMsg msg) {
  }
}