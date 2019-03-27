package com.github.jnoee.xo.starter.demo.core.cleaner;

import com.gitee.tianchaohongyu.jdcl.core.CleanException;
import com.gitee.tianchaohongyu.jdcl.core.Cleaner;
import com.gitee.tianchaohongyu.jdcl.core.CleanerAnnotation;
import com.github.jnoee.xo.jpa.dao.DaoUtils;
import com.github.jnoee.xo.jpa.entity.UuidEntity;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.SpringUtils;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 从数据库中读取数据。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD})
@CleanerAnnotation(ReadData.TempCleaner.class)
public @interface ReadData {

  /** 错误代码 */
  String value() default "e.read-data.not-found";

  /**
   * 清洗器实现
   */
  class TempCleaner implements Cleaner<UuidEntity, ReadData> {

    @Override
    public <R extends UuidEntity> R clean(R data, @NotNull @NotNull Class<R> defineType, ReadData annotation) {
      if (annotation == null) throw new CleanException("未指定注解:" + ReadData.class);
      if (data == null) return null;

      String id = data.getId();
      if (id == null) return null;

      @SuppressWarnings("unchecked")
      R entity = DaoUtils.getEntity(defineType, id);
      if (entity == null) {
        SpringUtils.getBean(MessageSource.class).thrown(annotation.value(), defineType.getSimpleName(), id);
      }

      return entity;
    }
  }
}
