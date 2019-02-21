package com.github.jnoee.xo.starter.demo.core.entity.work;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作人员。
 */
@Entity
@Table(name = "work_worker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Worker extends AuditEntity<Worker> implements AuthUser {
  /** 姓名 */
  @LogField(text = "姓名")
  private String name;

  /** 工作人员名 */
  @LogField(text = "工作人员名")
  private String username;

  /** 密码 */
  private String password;

  /** 启用状态 */
  @LogField(text = "启用状态")
  @Type(type = "IEnum")
  private EnabledStatus status = EnabledStatus.ENABLED;

  /** 排序 */
  @LogField(text = "排序")
  private Integer ordinal = 999;

  /** 默认职务 */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "defaultActorId")
  private Actor defaultActor;

  /** 工作人员职务 */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  @OrderBy("name")
  private List<Actor> actors = new ArrayList<>();

  /**
   * 判断是否超级管理员。
   * 
   * @return 返回是否超级管理员。
   */
  public Boolean isAdmin() {
    return "admin".equals(username);
  }

  @Override
  public Boolean getEnabled() {
    return status == EnabledStatus.ENABLED;
  }
}
