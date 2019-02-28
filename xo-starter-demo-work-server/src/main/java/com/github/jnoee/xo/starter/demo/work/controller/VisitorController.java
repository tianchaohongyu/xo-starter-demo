package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.starter.demo.work.service.VisitorService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping(path = "/visitor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户")
@RequiresPermissions("visitor:manage")
public class VisitorController {
  @Autowired
  private VisitorService visitorService;
//
//  @ApiOperation(value = "查询用户列表")
//  @GetMapping
//  public Page<UserVo> list(@Valid PageQuery query) {
//    return UserVo.forPage(visitorService.search(query));
//  }
//
//  @ApiOperation(value = "获取工作人员信息")
//  @GetMapping("{id}")
//  public UserVo get(@PathVariable String id) {
//    return UserVo.forView(workerService.get(id));
//  }
//
//  @ApiOperation(value = "新增工作人员")
//  @PostMapping
//  public void create(@RequestBody @Valid UserAddDto dto) {
//    workerService.create(dto.toUser());
//  }
//
//  @ApiOperation(value = "更新工作人员")
//  @PutMapping
//  public void update(@RequestBody @Valid UserEditDto dto) {
//    workerService.update(dto.toUser());
//  }
//
//  @ApiOperation(value = "停用工作人员")
//  @PatchMapping("disable/{id}")
//  public void disable(@PathVariable(value = "id") Worker user) {
//    workerService.disable(user);
//  }
//
//  @ApiOperation(value = "启用工作人员")
//  @PatchMapping("enable/{id}")
//  public void enable(@PathVariable(value = "id") Worker user) {
//    workerService.enable(user);
//  }
//
//  @ApiOperation(value = "重置密码")
//  @PatchMapping("reset/{id}")
//  public void reset(@PathVariable(value = "id") Worker user,
//                    @RequestParam(required = true) String managePassword) {
//    workerService.resetPassword(managePassword, user);
//  }
}
