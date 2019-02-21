package com.github.jnoee.xo.starter.demo.visit.controller;

import com.github.jnoee.xo.starter.demo.visit.dto.PwdChangeDto;
import com.github.jnoee.xo.starter.demo.visit.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "个人")
@RequiresAuthentication
public class VisitorController {

  @Autowired
  private VisitorService visitorService;

  @ApiOperation(value = "修改密码")
  @PatchMapping("change-pwd")
  @RequiresAuthentication
  public void changePwd(@RequestBody @Valid PwdChangeDto dto) {
    visitorService.changePassword(dto.getOldPwd(), dto.getNewPwd());
  }
}
