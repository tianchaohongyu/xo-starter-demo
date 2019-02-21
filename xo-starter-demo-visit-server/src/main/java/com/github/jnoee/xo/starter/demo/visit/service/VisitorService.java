package com.github.jnoee.xo.starter.demo.visit.service;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.AuthToken;
import com.github.jnoee.xo.auth.server.AuthUserService;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog.LogType;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.starter.demo.core.constants.AdminIds;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import com.github.jnoee.xo.utils.BeanUtils;
import com.github.jnoee.xo.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 普通用户管理。
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VisitorService implements AuthUserService<Visitor> {
  @Resource
  private Dao<Visitor> visitorDao;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private AuthHelper authHelper;

  @Override
  @SimpleLog(code = "l.worker.logon", vars = "username")
  public void login(String phone, String password) {
    AuthenticationToken token = new UsernamePasswordToken(phone, password);
    Subject subject = SecurityUtils.getSubject();
    subject.login(token);
  }

  @Override
  public Visitor getByUsername(String username) {
    return visitorDao.findUnique("phone", username);
  }

  @Override
  public AuthToken genAuthToken(String username) {
    Visitor user = getByUsername(username);
    AuthToken token = new AuthToken();
    token.setUsername(username);
    token.setPrivilegs(user.getIdentity().getPrivilegs());
    return token;
  }

  @Transactional(readOnly = true)
  public Visitor get(String id) {
    Visitor user = visitorDao.get(id);
    if (user == null) {
      messageSource.thrown("e.worker.get.not-exist", id);
    }
    return user;
  }

  @Transactional
  @SimpleLog(code = "l.worker.add", vars = "user.name")
  public void create(Visitor user) {
    if (!visitorDao.isUnique(user, "username")) {
      messageSource.thrown("e.worker.add.exist", user.getUsername());
    }
    if (StringUtils.isEmpty(user.getPassword())) {
      user.setPassword(authHelper.encodePassword(AdminIds.NEW_SALT));
    }
    visitorDao.save(user);
  }

  @Transactional
  @DetailLog(target = "user", code = "l.worker.edit", vars = "user.name", type = LogType.ALL)
  public void update(Visitor user) {
    if (!visitorDao.isUnique(user, "phone")) {
      messageSource.thrown("e.worker.add.exist", user.getUsername());
    }
    Visitor origUser = get(user.getId());
    BeanUtils.copyFields(user, origUser, "enabled");
  }

  @Transactional
  @DetailLog(target = "user", code = "l.worker.delete", vars = "user.name", type = LogType.ORIG)
  public void delete(Visitor user) {
    visitorDao.remove(user);
  }

  @Transactional
  @SimpleLog(code = "l.worker.enable", vars = "user.name")
  public void enable(Visitor user) {
    user.setStatus(EnabledStatus.ENABLED);
  }

  @Transactional
  @SimpleLog(code = "l.worker.disable", vars = "user.name")
  public void disable(Visitor user) {
    user.setStatus(EnabledStatus.DISABLED);
  }

  /**
   * 修改密码。
   *
   * @param oldPwd 原密码
   * @param newPwd 新密码
   */
  @Transactional
  @SimpleLog(code = "l.person.change-pwd")
  public void changePassword(String oldPwd, String newPwd) {
    Visitor user = getLogonUser();
    if (!authHelper.verifyPassword(oldPwd, user.getPassword())) {
      messageSource.thrown("e.person.change-pwd.old-pwd-wrong");
    }
    user.setPassword(authHelper.encodePassword(newPwd));
  }
}
