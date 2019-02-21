package com.github.jnoee.xo.starter.demo.core.entity.visit;

import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.core.enums.IdentityType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "visit_identity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Identity extends AuditEntity<Worker> {

  /** 默认身份代码 */
  public static final String DEFAULT_CODE = "default";

  private String name;

  /** 身份代码 */
  private String code;

  /** 关联身份类型 */
  @Type(type = "IEnum")
  private IdentityType type = IdentityType.DEFAULT;

  /** 权限 */
  public List<String> getPrivilegs() {
    return type.getPrivilegs();
  }
}
