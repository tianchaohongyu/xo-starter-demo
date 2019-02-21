package com.github.jnoee.xo.starter.demo.core.config;

import com.github.jnoee.xo.cache.config.AbstractCacheSettings;
import com.github.jnoee.xo.utils.ClassUtils;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CacheSettings")
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    List<Class<?>> list = ClassUtils.findClassesByAnnotationClass(Cache.class, "com.github.jnoee.xo.starter.demo.core.entity");
    String[] names = list.stream().map(Class::getName).toArray(String[]::new);
    addRegion(names);
  }
}
