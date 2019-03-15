package com.github.jnoee.xo.starter.demo.core.entity.visit;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.audit.annotation.LogField;
import com.github.jnoee.xo.starter.demo.core.entity.base.PeriodUuidEntity;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * 访客。
 */
@Indexed
@Entity
@Table(name = "visit_visitor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
public class Visitor extends PeriodUuidEntity implements AuthUser {

  /** 昵称 */
  @Field(analyze = Analyze.NO)
  @LogField(text = "昵称")
  @Length(message = "昵称长度应该为1~20字符", min = 1, max = 20)
  private String nickName;

  /** 头像 */
  private String imgUrl;

  /** 手机号码 */
  @Field(analyze = Analyze.NO)
  @LogField(text = "手机号码")
  private String mobile;

  /** 密码 */
  private String password;

  /** 身份 */
  @IndexedEmbedded(includePaths = {"id"})
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "identityId")
  private Identity identity;

  /** 启用状态 */
  @Field(analyze = Analyze.NO)
  @LogField(text = "启用状态")
  @Type(type = "IEnum")
  private EnabledStatus status = EnabledStatus.ENABLED;

  @Override
  public String getUsername() {
    return mobile;
  }

  @Override
  public Boolean getEnabled() {
    return status == EnabledStatus.ENABLED;
  }
}
