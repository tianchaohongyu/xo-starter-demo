package com.github.jnoee.xo.starter.demo.config;

import com.github.jnoee.xo.cache.config.AbstractCacheSettings;
import org.springframework.stereotype.Component;

@Component("com.github.jnoee.xo.starter.demo.config.CacheSettings")
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    addRegion("organ", "role", "worker", "actor", "visitor");
    addRegion("user", "identity");
  }
}
