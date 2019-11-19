package org.kurenai.demo.permission;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限队列消息
 *
 * @author liufuhong
 * @since 2019-07-05 16:59
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionQueueMsg implements Serializable {
  private static final long                    serialVersionUID = 88423564416873L;
  private              String                  appName;
  private              String                  version;
  private              boolean                 overWrite = false;
  private              List<PermissionElement> permissionElementList;
}
