package com.github.jnoee.xo.starter.demo.work.service;


import com.github.jnoee.xo.jpa.dao.Dao;
import com.github.jnoee.xo.jpa.query.Criteria;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户管理
 */
@Service
public class VisitorService {
  @Resource
  private Dao<Visitor> visitorDao;

  @Transactional(readOnly = true)
  public Page<Visitor> search(PageQuery query) {
    Criteria<Visitor> criteria = visitorDao.createCriteria();
    criteria.desc("createTime");
    if (query.getKeyword() != null && !query.getKeyword().isEmpty()) criteria.eq("phone", query.getKeyword());
    // 将系统管理员从搜索的工作人员结果中排除
    return visitorDao.findPage(criteria, query);
  }
}
