package com.github.jnoee.xo.starter.demo.entity.visit;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.starter.demo.entity.base.PeriodUuidEntity;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
import com.github.jnoee.xo.starter.demo.enums.VisitorType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 访客。
 */
@Entity
@Table(name = "Visitor")
@Cache(region = "visitor", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Visitor extends PeriodUuidEntity implements AuthUser {

  /** 昵称 */
  @LogField(text = "昵称")
  private String nickName;

  /** 手机号码 */
  @LogField(text = "手机号码")
  private String phone;

  /** 密码 */
  private String password;

  /** 关联身份类型 */
  @Type(type = "IEnum")
  private VisitorType type;

  /** 启用状态 */
  @LogField(text = "启用状态")
  @Type(type = "IEnum")
  private EnabledStatus status = EnabledStatus.ENABLED;

  @Override
  public String getUsername() {
    return id;
  }

  @Override
  public Boolean getEnabled() {
    return status == EnabledStatus.ENABLED;
  }
}
