package org.kurenai.demo.permission;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限元数据
 *
 * @author liufuhong
 * @since 2019-07-05 15:09
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionElement implements Serializable {
  private static final long   serialVersionUID = 88455566223L;
  private              String name;
  private              String path;
  private              String regex;
  //权限编码
  private              String permissionCode;
}
