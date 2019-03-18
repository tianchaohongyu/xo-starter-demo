package com.github.jnoee.xo.starter.demo.visit.controller;

import com.github.jnoee.xo.starter.demo.visit.dto.PwdChangeDto;
import com.github.jnoee.xo.starter.demo.visit.service.AuthService;
import com.github.jnoee.xo.starter.demo.visit.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/visitor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
@RequiresAuthentication
public class VisitorController {

  @Resource
  private VisitorService visitorService;
  @Resource
  private AuthService authService;

  @ApiOperation(value = "修改密码")
  @PatchMapping("change-pwd")
  public void changePwd(@RequestBody @Valid PwdChangeDto dto) {
    visitorService.changePassword(authService.getMe(), dto.getOldPwd(), dto.getNewPwd());
  }
}
