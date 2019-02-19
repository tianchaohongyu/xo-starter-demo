package com.github.jnoee.xo.starter.demo.controller;

import com.github.jnoee.xo.starter.demo.dto.RoleAddDto;
import com.github.jnoee.xo.starter.demo.dto.RoleEditDto;
import com.github.jnoee.xo.starter.demo.entity.work.Role;
import com.github.jnoee.xo.starter.demo.service.RoleService;
import com.github.jnoee.xo.starter.demo.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "角色")
@RequiresPermissions("role:manage")
public class RoleController {
  @Autowired
  private RoleService roleService;

  @ApiOperation(value = "查询角色列表")
  @GetMapping
  public List<RoleVo> list() {
    List<Role> roles = roleService.getAll();
    return RoleVo.forList(roles);
  }

  @ApiOperation(value = "获取角色信息")
  @GetMapping("{id}")
  public RoleVo get(@PathVariable String id) {
    return RoleVo.forView(roleService.get(id));
  }

  @ApiOperation(value = "新增角色")
  @PostMapping
  public void save(@RequestBody @Valid RoleAddDto dto) {
    roleService.create(dto.toRole());
  }

  @ApiOperation(value = "更新角色")
  @PutMapping
  public void update(@RequestBody @Valid RoleEditDto dto) {
    roleService.update(dto.toRole());
  }
}
