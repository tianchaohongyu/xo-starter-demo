package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.jpa.dao.DaoUtils;
import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.core.cleaner.ReadData;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Identity;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import com.github.jnoee.xo.starter.demo.work.dto.VisitorAddDto;
import com.github.jnoee.xo.starter.demo.work.dto.VisitorEditDto;
import com.github.jnoee.xo.starter.demo.work.service.VisitorService;
import com.github.jnoee.xo.starter.demo.work.vo.IdentityVo;
import com.github.jnoee.xo.starter.demo.work.vo.VisitorVo;
import com.github.jnoee.xo.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

/**
 * 用户控制器
 */
@RestController
@RequestMapping(path = "/visitors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
@RequiresPermissions("visitor:manage")
public class VisitorController {

  @Autowired
  private VisitorService visitorService;

  @ApiOperation(value = "查询用户列表")
  @GetMapping
  public Page<VisitorVo> list(@Valid TempQuery query) {
    return visitorService.search(query, StringUtils.blankAsNull(query.identityId), query.status).map(it -> {
      VisitorVo vo = new VisitorVo();
      vo.setId(it.getId());
      vo.setNickName(it.getNickName());
      vo.setImgUrl(it.getImgUrl());
      vo.setMobile(it.getMobile());
      vo.setIdentity(new IdentityVo(it.getIdentity().getId(), it.getIdentity().getName()));
      vo.setStatus(it.getStatus().toVo());
      vo.setCreateTime(it.getCreateTime());
      vo.setUpdateTime(it.getUpdateTime());
      return vo;
    });
  }

  @ApiOperation(value = "获取用户信息")
  @GetMapping("{id}")
  public VisitorVo get(@PathVariable String id) {
    Visitor it = visitorService.get(id);
    VisitorVo vo = new VisitorVo();
    vo.setId(it.getId());
    vo.setNickName(it.getNickName());
    vo.setImgUrl(it.getImgUrl());
    vo.setMobile(it.getMobile());
    vo.setIdentity(new IdentityVo(it.getIdentity().getId(), it.getIdentity().getName()));
    vo.setStatus(it.getStatus().toVo());
    vo.setCreateTime(it.getCreateTime());
    vo.setUpdateTime(it.getUpdateTime());
    return vo;
  }

  @ApiOperation(value = "新增用户")
  @PostMapping
  public void create(@RequestBody @Valid VisitorAddDto dto) {
    Visitor it = new Visitor();
    it.setNickName(dto.getNickName());
    it.setMobile(StringUtils.blankAsNull(dto.getMobile()));
    it.setPassword(StringUtils.blankAsNull(dto.getPassword()));
    it.setImgUrl(StringUtils.blankAs(dto.getImgUrl(), "/static/images/head/" + new Random().nextInt(2000) + ".jpg"));
    it.setIdentity(DaoUtils.getEntity(Identity.class, dto.getIdentityId()));
    visitorService.create(it);
  }

  @ApiOperation(value = "更新用户")
  @PutMapping
  public void update(@RequestBody @Valid VisitorEditDto dto) {
    Visitor it = new Visitor();
    it.setId(dto.getId());
    it.setNickName(dto.getNickName());
    it.setMobile(StringUtils.blankAsNull(dto.getMobile()));
    it.setPassword(StringUtils.blankAsNull(dto.getPassword()));
    it.setImgUrl(StringUtils.blankAsNull(dto.getImgUrl()));
    it.setIdentity(DaoUtils.getEntity(Identity.class, dto.getIdentityId()));
    it.setStatus(null);
    visitorService.update(it);
  }

  @ApiOperation(value = "停用用户")
  @PatchMapping("disable/{id}")
  public void disable(@ReadData @PathVariable(value = "id") Visitor visitor) {
    visitorService.disable(visitor);
  }

  @ApiOperation(value = "启用用户")
  @PatchMapping("enable/{id}")
  public void enable(@ReadData @PathVariable(value = "id") Visitor visitor) {
    visitorService.enable(visitor);
  }

  @Data
  static public class TempQuery extends PageQuery {

    @ApiModelProperty(value = "身份id", example = "1")
    private String identityId;

    @ApiModelProperty(value = "状态", example = "1")
    private EnabledStatus status;

    public TempQuery() {

    }
  }
}
