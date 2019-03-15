package com.github.jnoee.xo.starter.demo.work.service;


import com.github.jnoee.xo.jpa.audit.annotation.DetailLog;
import com.github.jnoee.xo.jpa.audit.annotation.SimpleLog;
import com.github.jnoee.xo.jpa.search.dao.FullTextDao;
import com.github.jnoee.xo.jpa.search.query.FullTextCriteria;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import com.github.jnoee.xo.utils.BeanUtils;
import org.apache.lucene.search.SortField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户管理
 */
@Service
public class VisitorService {
  @Resource
  private FullTextDao<Visitor> visitorDao;
  @Resource
  private MessageSource messageSource;

  @Transactional(readOnly = true)
  public Page<Visitor> search(PageQuery query) {
    FullTextCriteria criteria = visitorDao.createFullTextCriteria();
    criteria.desc("createTime", SortField.Type.STRING);
    return visitorDao.searchPage(criteria, query);
  }

  @Transactional(readOnly = true)
  public Visitor get(String id) {
    Visitor visitor = visitorDao.get(id);
    if (visitor == null) {
      messageSource.thrown("e.visitor.get.not-exist", id);
    }
    return visitor;
  }

  @Transactional
  @SimpleLog(code = "l.visitor.add", vars = {"visitor.mobile", "visitor.nickName"})
  public void create(Visitor visitor) {
    if (!visitorDao.isUnique(visitor, "mobile"))
      messageSource.thrown("e.visitor.add.mobile.exist", visitor.getMobile());
    visitorDao.save(visitor);
  }

  @Transactional
  @DetailLog(target = "visitor", code = "l.visitor.edit", vars = {"visitor.mobile", "visitor.nickName"}, type = DetailLog.LogType.ALL)
  public void update(Visitor visitor) {
    if (!visitorDao.isUnique(visitor, "mobile"))
      messageSource.thrown("e.visitor.add.mobile.exist", visitor.getMobile());
    Visitor origVisitor = get(visitor.getId());
    BeanUtils.copyFields(visitor, origVisitor);
    origVisitor.setMobile(visitor.getMobile()); //手机号码如果前端置为null也将其
  }

  @Transactional
  @SimpleLog(code = "l.visitor.enable", vars = {"visitor.mobile", "visitor.nickName"})
  public void enable(Visitor visitor) {
    visitor.setStatus(EnabledStatus.ENABLED);
  }

  @Transactional
  @SimpleLog(code = "l.visitor.disable", vars = {"visitor.mobile", "visitor.nickName"})
  public void disable(Visitor visitor) {
    visitor.setStatus(EnabledStatus.DISABLED);
  }
}
