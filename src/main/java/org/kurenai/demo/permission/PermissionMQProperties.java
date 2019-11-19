package org.kurenai.demo.permission;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MQ配置文件
 *
 * @author liufuhong
 * @since 2019-08-28 16:03
 */

@Data
@ConfigurationProperties(prefix = "permission.mq")
public class PermissionMQProperties {
  private int maxAttempts = 3;  //重试次数
  private int intervals = 30 * 1000; //间隔时间
}