package com.github.jnoee.xo.starter.demo.entity.client;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * 工作人员。
 */
@Entity
@Table(name = "User")
@Cache(region = "user", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class User extends AuditEntity<User> implements AuthUser {

  /** 昵称 */
  @LogField(text = "昵称")
  private String nickName;

  /** 手机号码 */
  @LogField(text = "工作人员名")
  private String phone;

  /** 密码 */
  private String password;

  /** 关联身份 */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "identityId")
  private Identity identity;

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
