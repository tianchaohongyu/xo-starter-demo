package com.github.jnoee.xo.starter.demo;

import com.gitee.tianchaohongyu.jdcl.configuration.EnableRequestMappingParamsClean;
import com.github.jnoee.xo.ienum.api.EnableIEnumApi;
import com.github.jnoee.xo.jpa.search.dao.FullTextDaoScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableIEnumApi
@EntityScan
@FullTextDaoScan
//@DaoScan
@EnableRequestMappingParamsClean
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
