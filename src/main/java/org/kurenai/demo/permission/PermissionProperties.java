package org.kurenai.demo.permission;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限配置信息
 *
 * @author liufuhong
 * @since 2019-06-14 9:26
 */

@Data
@Configuration
@ConfigurationProperties("permission")
public class PermissionProperties {
  private boolean enabled = true;
  private boolean overWrite = false;
  private String version = "1.0.0";
  private String scanPackage = "com.iotlead.platform";
  private long lockValidityTime = 1000L;
}