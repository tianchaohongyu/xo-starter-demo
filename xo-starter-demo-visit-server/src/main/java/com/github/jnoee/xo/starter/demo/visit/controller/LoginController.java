package com.github.jnoee.xo.starter.demo.visit.controller;

import com.github.jnoee.xo.privileg.PrivilegManager;
import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.visit.dto.LoginDto;
import com.github.jnoee.xo.starter.demo.visit.service.VisitorService;
import com.github.jnoee.xo.starter.demo.visit.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "认证")
public class LoginController {

  @Autowired
  private VisitorService visitorService;
  @Autowired
  private PrivilegManager privilegManager;

  @ApiOperation(value = "登录",
          notes = "登录成功后，Header中包含x-auth-token令牌。客户端保存token，并在后续访问的Header中包含该x-auth-token。")
  @PostMapping(path = "login")
  public LoginVo login(@RequestBody @Valid LoginDto dto) {
    visitorService.login(dto.getUsername(), dto.getPassword());
    Visitor user = visitorService.getLogonUser();
    List<String> privilegs = visitorService.getAuthToken().getPrivilegs();
    return new LoginVo(user, privilegs);
  }

  @ApiOperation(value = "退出登录")
  @DeleteMapping("logout")
  @RequiresAuthentication
  public void logout() {
    visitorService.logout();
  }
}
