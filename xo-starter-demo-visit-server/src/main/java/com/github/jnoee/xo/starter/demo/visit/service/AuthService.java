package com.github.jnoee.xo.starter.demo.visit.service;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.AuthToken;
import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.auth.server.AuthUserService;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.core.service.CoreVisitorService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * shiro认证服务
 */
@Service
public class AuthService {

  @Resource
  private ShiroUserService shiroService;
  @Resource
  private CoreVisitorService coreVisitorService;
  @Resource
  private Dao<Visitor> visitorDao;

  /**
   * 登录,在此之前请确认密码是否存在
   *
   * @param visitor 要登录的用户
   */
  public void login(Visitor visitor) {
    shiroService.login(visitor.getId(), null);
  }

  /**
   * 登录
   *
   * @param mobile   手机
   * @param password 面膜
   * @return 如果密码正确返回用户信息
   */
  public Visitor login(String mobile, String password) {
    Visitor visitor = coreVisitorService.getByMobile(mobile);
    if (visitor == null || !coreVisitorService.checkPassword(visitor, password)) throw new UnknownAccountException();
    login(visitor);
    return visitor;
  }

  /**
   * 获取当前操作的用户
   *
   * @return 返回当前登陆的用户
   */
  public Visitor getMe() {
    return visitorDao.get(shiroService.getLogonUser().getUsername());
  }

  /**
   * 获取当前用户权限
   *
   * @return 返回权限列表
   */
  public List<String> getPrivilegs() {
    return shiroService.getAuthToken().getPrivilegs();
  }

  /**
   * 注销当前用户
   */
  public void logout() {
    shiroService.logout();
  }

  /**
   * shiro认证管理服务。
   */
  @Service
  @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
  static class ShiroUserService implements AuthUserService<ShiroUserService.ShiroUser> {

    private final static String DEFAULT_P = "";
    @Resource
    private AuthHelper authHelper;
    @Resource
    private Dao<Visitor> visitorDao;

    @Override
    public void login(String id, String outmoded) {
      AuthenticationToken token = new UsernamePasswordToken(id, DEFAULT_P);
      Subject subject = SecurityUtils.getSubject();
      subject.login(token);
    }

    @Override
    public ShiroUser getByUsername(String id) {
      Visitor visitor = visitorDao.get(id);
      return visitor == null ? null : new ShiroUser(visitor.getId(), authHelper.encodePassword(DEFAULT_P), visitor.getEnabled());
    }

    @Override
    public AuthToken genAuthToken(String id) {
      Visitor visitor = visitorDao.get(id);
      AuthToken token = new AuthToken();
      token.setUsername(id);
      token.setPrivilegs(visitor.getIdentity().getPrivilegs());
      return token;
    }

    /**
     * shiro用户数据
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ShiroUser implements AuthUser {
      /** 用户名 */
      private String username;
      /** 密码 */
      private String password;
      /** 状态 */
      private boolean enabled;

      @Override
      public Boolean getEnabled() {
        return enabled;
      }
    }
  }
}