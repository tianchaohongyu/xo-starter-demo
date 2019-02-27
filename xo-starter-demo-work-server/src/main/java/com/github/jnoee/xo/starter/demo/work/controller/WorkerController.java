package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.model.PageQuery;
import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.work.dto.UserAddDto;
import com.github.jnoee.xo.starter.demo.work.dto.UserEditDto;
import com.github.jnoee.xo.starter.demo.work.service.WorkerService;
import com.github.jnoee.xo.starter.demo.work.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/workers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "工作人员")
@RequiresPermissions("worker:manage")
public class WorkerController {
  @Autowired
  private WorkerService workerService;

  @ApiOperation(value = "查询工作人员列表")
  @GetMapping
  public Page<UserVo> list(@Valid PageQuery query) {
    return UserVo.forPage(workerService.search(query));
  }

  @ApiOperation(value = "获取工作人员信息")
  @GetMapping("{id}")
  public UserVo get(@PathVariable String id) {
    return UserVo.forView(workerService.get(id));
  }

  @ApiOperation(value = "新增工作人员")
  @PostMapping
  public void create(@RequestBody @Valid UserAddDto dto) {
    workerService.create(dto.toUser());
  }

  @ApiOperation(value = "更新工作人员")
  @PutMapping
  public void update(@RequestBody @Valid UserEditDto dto) {
    workerService.update(dto.toUser());
  }

  @ApiOperation(value = "停用工作人员")
  @PatchMapping("disable/{id}")
  public void disable(@PathVariable(value = "id") Worker user) {
    workerService.disable(user);
  }

  @ApiOperation(value = "启用工作人员")
  @PatchMapping("enable/{id}")
  public void enable(@PathVariable(value = "id") Worker user) {
    workerService.enable(user);
  }

  @ApiOperation(value = "重置密码")
  @PatchMapping("reset/{id}")
  public void reset(@PathVariable(value = "id") Worker user,
                    @RequestParam(required = true) String managePassword) {
    workerService.resetPassword(managePassword, user);
  }
}
