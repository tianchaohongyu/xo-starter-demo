package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.starter.demo.core.entity.visit.Identity;
import com.github.jnoee.xo.starter.demo.work.dto.IdentityAddDto;
import com.github.jnoee.xo.starter.demo.work.dto.IdentityEditDto;
import com.github.jnoee.xo.starter.demo.work.service.IdentityService;
import com.github.jnoee.xo.starter.demo.work.vo.IdentityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/identity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户身份")
@RequiresPermissions("visitor:manage")
public class IdentityController {

  @Autowired
  private IdentityService identityService;

  @ApiOperation(value = "查询用户身份列表")
  @GetMapping
  public List<IdentityVo> list() {
    List<Identity> identitys = identityService.getAll();
    return identitys.stream().map(it -> {
      IdentityVo vo = new IdentityVo();
      vo.setId(it.getId());
      vo.setName(it.getName());
      vo.setCode(it.getCode());
      vo.setType(it.getType());
      return vo;
    }).collect(Collectors.toList());
  }

  @ApiOperation(value = "获取用户身份信息")
  @GetMapping("{id}")
  public IdentityVo get(@PathVariable String id) {
    Identity identity = identityService.get(id);
    IdentityVo vo = new IdentityVo();
    vo.setId(identity.getId());
    vo.setName(identity.getName());
    vo.setCode(identity.getCode());
    vo.setType(identity.getType());
    return vo;
  }

  @ApiOperation(value = "新增用户身份")
  @PostMapping
  public void save(@RequestBody @Valid IdentityAddDto dto) {
    Identity identity = new Identity();
    identity.setName(dto.getName());
    identity.setCode(dto.getCode());
    identity.setType(dto.getType());
    identityService.create(identity);
  }

  @ApiOperation(value = "更新用户身份")
  @PutMapping
  public void update(@RequestBody @Valid IdentityEditDto dto) {
    Identity identity = new Identity();
    identity.setId(dto.getId());
    identity.setName(dto.getName());
    identity.setCode(dto.getCode());
    identity.setType(dto.getType());
    identityService.create(identity);
    identityService.update(identity);
  }
}
