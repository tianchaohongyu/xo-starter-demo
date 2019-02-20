package com.github.jnoee.xo.starter.demo.controller;

import com.github.jnoee.xo.privileg.PrivilegManager;
import com.github.jnoee.xo.privileg.Privilegs;
import com.github.jnoee.xo.starter.demo.dto.LoginDto;
import com.github.jnoee.xo.starter.demo.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.service.WorkerService;
import com.github.jnoee.xo.starter.demo.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "认证")
public class AuthController {
  @Autowired
  private WorkerService workerService;
  @Autowired
  private PrivilegManager privilegManager;

  @ApiOperation(value = "登录",
      notes = "登录成功后，Header中包含x-auth-token令牌。客户端保存token，并在后续访问的Header中包含该x-auth-token。")
  @PostMapping(path = "login")
  public LoginVo login(@RequestBody @Valid LoginDto dto) {
    workerService.login(dto.getUsername(), dto.getPassword());
    Worker user = workerService.getLogonUser();
    List<String> privilegs = workerService.getAuthToken().getPrivilegs();
    return new LoginVo(user, privilegs);
  }

  @ApiOperation(value = "退出登录")
  @DeleteMapping("logout")
  @RequiresAuthentication
  public void logout() {
    workerService.logout();
  }

  @ApiOperation(value = "获取系统所有权限列表")
  @GetMapping(path = "privilegs")
  @RequiresPermissions("role:manage")
  public Privilegs getPrivilegs() {
    return privilegManager.getPrivilegs();
  }
}
