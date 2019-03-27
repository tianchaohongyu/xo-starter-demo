package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.constant.ValidGroup;
import com.github.jnoee.xo.starter.demo.core.cleaner.ReadData;
import com.github.jnoee.xo.starter.demo.core.entity.work.Organ;
import com.github.jnoee.xo.starter.demo.work.dto.OrganAddDto;
import com.github.jnoee.xo.starter.demo.work.dto.OrganEditDto;
import com.github.jnoee.xo.starter.demo.work.service.OrganService;
import com.github.jnoee.xo.starter.demo.work.service.WorkerService;
import com.github.jnoee.xo.starter.demo.work.vo.OrganVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/organs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "机构")
@RequiresPermissions("organ:manage")
public class OrganController {
  @Autowired
  private OrganService organService;
  @Autowired
  private WorkerService workerService;

  @ApiOperation(value = "获取机构树信息")
  @GetMapping
  public OrganVo tree() {
    return OrganVo.forTree(workerService.getCurrentOrgan());
  }

  @ApiOperation(value = "获取机构信息")
  @GetMapping("{id}")
  public OrganVo get(@PathVariable String id) {
    return OrganVo.forView(organService.get(id));
  }

  @ApiOperation(value = "新增机构")
  @PostMapping
  public void create(@RequestBody @Validated(ValidGroup.Add.class) OrganAddDto dto) {
    organService.create(dto.toOrgan());
  }

  @ApiOperation(value = "更新机构")
  @PutMapping
  public void update(@RequestBody @Valid OrganEditDto dto) {
    organService.update(dto.toOrgan());
  }

  @ApiOperation(value = "启用机构")
  @PatchMapping("enable/{id}")
  public void enable(@ReadData @PathVariable(value = "id") Organ organ) {
    organService.enable(organ);
  }

  @ApiOperation(value = "停用机构")
  @PatchMapping("disable/{id}")
  public void disable(@ReadData @PathVariable(value = "id") Organ organ) {
    organService.disable(organ);
  }
}
