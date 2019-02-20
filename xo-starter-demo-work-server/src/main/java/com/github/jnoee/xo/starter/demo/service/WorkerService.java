package com.github.jnoee.xo.starter.demo.service;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.AuthToken;
import com.github.jnoee.xo.auth.server.AuthUserService;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog.LogType;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.jpa.query.Criteria;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.constants.AdminIds;
import com.github.jnoee.xo.starter.demo.entity.work.Actor;
import com.github.jnoee.xo.starter.demo.entity.work.Organ;
import com.github.jnoee.xo.starter.demo.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.enums.EnabledStatus;
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
 * 工作人员管理。
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WorkerService implements AuthUserService<Worker> {
  @Resource
  private Dao<Worker> workerDao;
  @Resource
  private Dao<Actor> actorDao;
  @Autowired
  private AuthHelper authHelper;
  @Autowired
  private MessageSource messageSource;

  @Override
  @SimpleLog(code = "l.worker.logon", vars = "username")
  public void login(String username, String password) {
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    Subject subject = SecurityUtils.getSubject();
    subject.login(token);
  }

  @Override
  public Worker getByUsername(String username) {
    return workerDao.findUnique("username", username);
  }

  @Override
  public AuthToken genAuthToken(String username) {
    Worker user = getByUsername(username);
    AuthToken token = new AuthToken();
    token.setUsername(username);
    token.setPrivilegs(user.getDefaultActor().getRole().getPrivilegs());
    return token;
  }

  @Transactional(readOnly = true)
  public Page<Worker> search(PageQuery query) {
    Criteria<Worker> criteria = workerDao.createCriteria();
    criteria.desc("createTime");
    // 将系统管理员从搜索的工作人员结果中排除
    criteria.notEq("id", AdminIds.USER_ID);
    return workerDao.findPage(criteria, query, "username", "name");
  }

  @Transactional(readOnly = true)
  public Worker get(String id) {
    Worker user = workerDao.get(id);
    if (user == null) {
      messageSource.thrown("e.worker.get.not-exist", id);
    }
    return user;
  }

  @Transactional
  @SimpleLog(code = "l.worker.add", vars = "user.name")
  public void create(Worker user) {
    if (!workerDao.isUnique(user, "username")) {
      messageSource.thrown("e.worker.add.exist", user.getUsername());
    }
    if (StringUtils.isEmpty(user.getPassword())) {
      user.setPassword(authHelper.encodePassword(AdminIds.NEW_SALT));
    }
    workerDao.save(user);

    Actor defaultActor = user.getDefaultActor();
    defaultActor.setUser(user);
    actorDao.save(defaultActor);
  }

  @Transactional
  @DetailLog(target = "user", code = "l.worker.edit", vars = "user.name", type = LogType.ALL)
  public void update(Worker user) {
    if (!workerDao.isUnique(user, "username")) {
      messageSource.thrown("e.worker.add.exist", user.getUsername());
    }
    Worker origUser = get(user.getId());
    BeanUtils.copyFields(user, origUser, "enabled");
  }

  @Transactional
  @DetailLog(target = "user", code = "l.worker.delete", vars = "user.name", type = LogType.ORIG)
  public void delete(Worker user) {
    workerDao.remove(user);
  }

  @Transactional
  @SimpleLog(code = "l.worker.enable", vars = "user.name")
  public void enable(Worker user) {
    user.setStatus(EnabledStatus.ENABLED);
  }

  @Transactional
  @SimpleLog(code = "l.worker.disable", vars = "user.name")
  public void disable(Worker user) {
    user.setStatus(EnabledStatus.DISABLED);
  }

  /**
   * 重置密码。
   *
   * @param managePassword 管理员密码
   * @param user 重置工作人员
   */
  @Transactional
  @SimpleLog(code = "l.worker.reset-pwd", vars = "user.name")
  public void resetPassword(String managePassword, Worker user) {
    if (!authHelper.verifyPassword(managePassword, getLogonUser().getPassword())) {
      messageSource.thrown("e.worker.reset-pwd.admin-pwd-wrong");
    }
    user.setPassword(authHelper.encodePassword(AdminIds.NEW_SALT));
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
    Worker user = getLogonUser();
    if (!authHelper.verifyPassword(oldPwd, user.getPassword())) {
      messageSource.thrown("e.person.change-pwd.old-pwd-wrong");
    }
    user.setPassword(authHelper.encodePassword(newPwd));
  }

  /**
   * 当前登录工作人员切换到指定的职务。
   *
   * @param actorId 职务ID
   */
  @Transactional
  public void changeActor(String actorId) {
    Worker currentUser = getLogonUser();
    Actor actor = actorDao.get(actorId);
    if (!currentUser.getActors().contains(actor)) {
      messageSource.thrown("e.actor.change.exceed");
    }
    currentUser.setDefaultActor(actor);

    AuthToken authToken = new AuthToken(currentUser.getUsername(), actor.getRole().getPrivilegs());
    authHelper.applyToken(authToken);
  }

  /**
   * 获取当前登录工作人员机构。
   * 
   * @return 返回当前登录工作人员机构。
   */
  @Transactional(readOnly = true)
  public Organ getCurrentOrgan() {
    return getLogonUser().getDefaultActor().getOrgan();
  }
}
