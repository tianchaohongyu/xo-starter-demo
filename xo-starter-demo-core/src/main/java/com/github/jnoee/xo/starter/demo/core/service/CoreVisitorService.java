package com.github.jnoee.xo.starter.demo.core.service;

import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 普通用户服务
 */
@Service
public class CoreVisitorService {

  @Resource
  private Dao<Visitor> visitorDao;

  /**
   * 通过手机号码获取用户
   *
   * @param mobile 手机号码
   */
  public Visitor getByMobile(String mobile) {
    return visitorDao.findUnique("mobile", mobile);
  }

  /**
   * 检查密码
   *
   * @param visitor  用户
   * @param password 欲验证的密码
   * @return 如果正确返回true, 错误返回false
   */
  public boolean checkPassword(Visitor visitor, String password) {
    return encodePassword(password, visitor.getSalt()).equals(visitor.getPassword());
  }

  /**
   * 检查安全密码
   *
   * @param visitor  用户
   * @param password 欲验证的密码
   * @return 如果正确返回true, 错误返回false
   */
  public boolean checkSafePassword(Visitor visitor, String password) {
    return encodePassword(password, visitor.getSalt()).equals(visitor.getSafePassword());
  }

  /**
   * 创建普通用户
   *
   * @param visitor 用户
   */
  @Transactional
  public void create(Visitor visitor) {
    if (StringUtils.isBlank(visitor.getSalt()))
      visitor.setSalt(DigestUtils.sha512Hex(String.valueOf(System.nanoTime())).substring(0, 100));
    if (visitor.getPassword() != null) updatePassword(visitor, visitor.getPassword());
    if (visitor.getSafePassword() != null) updateSafePassword(visitor, visitor.getSafePassword());
    visitorDao.save(visitor);
  }

  /**
   * 更新密码
   *
   * @param visitor  用户
   * @param password 新密码
   */
  @Transactional
  public void updatePassword(Visitor visitor, String password) {
    visitor.setPassword(encodePassword(password, visitor.getSalt()));
  }

  /**
   * 更新安全密码
   *
   * @param visitor  用户
   * @param password 新密码
   */
  @Transactional
  public void updateSafePassword(Visitor visitor, String password) {
    visitor.setSafePassword(encodePassword(password, visitor.getSalt()));
  }


  /**
   * 编码密码
   *
   * @param password 密码
   * @param salt     盐
   * @return 编码后的密码
   */
  public String encodePassword(String password, String salt) {
    return DigestUtils.md5Hex(DigestUtils.sha512(DigestUtils.md5Hex(password) + salt));
  }
}
