package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.starter.demo.work.dto.PwdChangeDto;
import com.github.jnoee.xo.starter.demo.work.service.WorkerService;
import com.github.jnoee.xo.starter.demo.work.vo.ActorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/person", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "个人")
@RequiresAuthentication
public class PersonController {
  @Autowired
  private WorkerService workerService;

  @ApiOperation(value = "修改密码")
  @PatchMapping("change-pwd")
  @RequiresAuthentication
  public void changePwd(@RequestBody @Valid PwdChangeDto dto) {
    workerService.changePassword(dto.getOldPwd(), dto.getNewPwd());
  }

  @ApiOperation(value = "获取职务列表")
  @GetMapping("actors")
  public List<ActorVo> listActors() {
    return ActorVo.forList(workerService.getLogonUser());
  }

  @ApiOperation(value = "切换职务", responseContainer = "List")
  @ApiImplicitParam(name = "actorId", value = "职务ID", required = true)
  @PatchMapping("actor-switch")
  @RequiresAuthentication
  public List<String> changeActor(@RequestParam(required = true) @NotBlank String actorId) {
    workerService.changeActor(actorId);
    return workerService.getLogonUser().getDefaultActor().getRole().getPrivilegs();
  }
}
