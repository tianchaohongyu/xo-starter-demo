package com.github.jnoee.xo.starter.demo.core.entity.work;

import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色。
 */
@Entity
@Table(name = "work_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Role extends AuditEntity<Worker> {

  /** 名称 */
  @LogField(text = "名称")
  private String name;

  /** 权限 */
  @LogField(text = "权限")
  private List<String> privilegs = new ArrayList<>();

  /** 关联职务 */
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
      orphanRemoval = true)
  private List<Actor> actors = new ArrayList<>();
}
