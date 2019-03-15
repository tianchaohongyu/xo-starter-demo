package com.github.jnoee.xo.starter.demo.core.entity.base;

import com.github.jnoee.xo.jpa.entity.UuidEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.SortableField;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 时间段记录实体基类
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PeriodUuidEntity extends UuidEntity {

  /** 实体创建时间 */
  @CreatedDate
  @Field(analyze = Analyze.NO)
  @SortableField
  private LocalDateTime createTime;

  /** 实体更新时间 */
  @LastModifiedDate
  @Field(analyze = Analyze.NO)
  @SortableField
  private LocalDateTime updateTime;
}
