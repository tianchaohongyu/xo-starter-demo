package com.github.jnoee.xo.starter.demo.work.service;

import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.DetailLog.LogType;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.jpa.query.Criteria;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Identity;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.utils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户身份管理。
 */
@Service
public class IdentityService {
  @Resource
  private Dao<Identity> identityDao;
  @Resource
  private Dao<Visitor> visitorDao;
  @Resource
  private MessageSource messageSource;

  @Transactional(readOnly = true)
  public Identity get(String identityId) {
    return identityDao.get(identityId);
  }

  @Transactional(readOnly = true)
  public List<Identity> getAll() {
    return identityDao.getAll("createTime", true);
  }

  @Transactional(readOnly = true)
  public Page<Identity> search(PageQuery query) {
    Criteria<Identity> criteria = identityDao.createCriteria();
    criteria.desc("createTime");
    return identityDao.findPage(criteria, query, "code", "name");
  }

  @Transactional
  @SimpleLog(code = "l.identity.add", vars = "identity.name")
  public void create(Identity identity) {
    if (!identityDao.isUnique(identity, "name")) messageSource.thrown("e.identity.add.name.exist", identity.getName());
    if (!identityDao.isUnique(identity, "code")) messageSource.thrown("e.identity.add.code.exist", identity.getCode());
    identityDao.save(identity);
  }

  @Transactional
  @DetailLog(target = "identity", code = "l.identity.edit", vars = "identity.name", type = LogType.ALL)
  public void update(Identity identity) {
    if (!identityDao.isUnique(identity, "name")) messageSource.thrown("e.identity.edit.name.exist", identity.getName());
    if (!identityDao.isUnique(identity, "code")) messageSource.thrown("e.identity.edit.code.exist", identity.getCode());
    Identity origIdentity = get(identity.getId());
    if (origIdentity.getCode().equals(Identity.DEFAULT_CODE) && identity.getCode() != null && !identity.getCode().equals(Identity.DEFAULT_CODE))
      messageSource.thrown("e.identity.edit.code.defaulted.not-supported");
    BeanUtils.copyFields(identity, origIdentity);
  }

  @Transactional
  @SimpleLog(code = "l.identity.delete", vars = {"identity.code", "identity.name"})
  public void delete(Identity identity) {
    if (identity.getCode().equals(Identity.DEFAULT_CODE)) messageSource.thrown("e.identity.delete.defaulted.not-supported", identity.getName());
    if (visitorDao.findUnique("identity", identity) != null) messageSource.thrown("e.identity.delete.name.used", identity.getName());
    identityDao.remove(identity);
  }
}
