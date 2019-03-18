package com.github.jnoee.xo.starter.demo.visit.service;

import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.core.service.CoreVisitorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户服务
 */
@Service
public class VisitorService {

  @Resource
  private CoreVisitorService coreVisitorService;
  @Resource
  private MessageSource messageSource;

  /**
   * 修改密码
   *
   * @param visitor
   * @param oldPassword
   * @param newPassword
   */
  @Transactional
  public void changePassword(Visitor visitor, String oldPassword, String newPassword) {
    if (!coreVisitorService.checkPassword(visitor, oldPassword)) messageSource.thrown("e.visitor.passwordError");
    coreVisitorService.updatePassword(visitor, newPassword);
  }
}
