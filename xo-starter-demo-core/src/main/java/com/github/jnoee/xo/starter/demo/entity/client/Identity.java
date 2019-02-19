package com.github.jnoee.xo.starter.demo.entity.client;

import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.jpa.audit.entity.AuditEntity;
import com.github.jnoee.xo.starter.demo.entity.work.Worker;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色身份。
 */
@Entity
@Table(name = "Identity")
@Cache(region = "identity", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Identity extends AuditEntity<Worker> {

  /** 名称 */
  @LogField(text = "名称")
  private String name;

  /** 身份代码 */
  private String code;

  /** 权限 */
  @LogField(text = "权限")
  private List<String> privilegs = new ArrayList<>();
}
