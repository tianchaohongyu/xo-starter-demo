package com.github.jnoee.xo.starter.demo.visit.controller;

import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import com.github.jnoee.xo.starter.demo.visit.dto.LoginDto;
import com.github.jnoee.xo.starter.demo.visit.service.AuthService;
import com.github.jnoee.xo.starter.demo.visit.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "认证")
public class AuthController {

  @Autowired
  private AuthService authService;

  @ApiOperation(value = "登录", notes = "登录成功后，Header中包含x-auth-token令牌。客户端保存token，并在后续访问的Header中包含该x-auth-token。")
  @PostMapping(path = "login")
  public LoginVo login(@RequestBody @Valid LoginDto dto) {
    Visitor visitor = authService.login(dto.getMobile(), dto.getPassword());
    List<String> privilegs = authService.getPrivilegs();
    return new LoginVo(visitor, privilegs);
  }

  @ApiOperation(value = "退出登录")
  @DeleteMapping("logout")
  public void logout() {
    authService.logout();
  }
}
