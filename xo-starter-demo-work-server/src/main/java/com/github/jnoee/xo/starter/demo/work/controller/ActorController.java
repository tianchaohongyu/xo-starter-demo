package com.github.jnoee.xo.starter.demo.work.controller;

import com.github.jnoee.xo.starter.demo.core.cleaner.ReadData;
import com.github.jnoee.xo.starter.demo.core.entity.work.Actor;
import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.work.dto.ActorAddDto;
import com.github.jnoee.xo.starter.demo.work.dto.ActorEditDto;
import com.github.jnoee.xo.starter.demo.work.service.ActorService;
import com.github.jnoee.xo.starter.demo.work.vo.ActorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/workers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "工作人员")
@RequiresRoles("worker")
public class ActorController {
  @Autowired
  private ActorService actorService;

  @ApiOperation(value = "查询职务列表")
  @GetMapping("{userId}/actors")

  @ReadData
  public List<ActorVo> list(@PathVariable(value = "userId") Worker user) {
    return ActorVo.forList(user);
  }

  @ReadData
  @ApiOperation(value = "新增职务")
  @PostMapping("{userId}/actors")
  public void create(@PathVariable(value = "userId") Worker user,
                     @RequestBody @Valid ActorAddDto dto) {
    actorService.create(dto.toActor(user));
  }

  @ReadData
  @ApiOperation(value = "更新职务")
  @PutMapping("{userId}/actors")
  public void update(@PathVariable(value = "userId") Worker user,
                     @RequestBody @Valid ActorEditDto dto) {
    actorService.update(dto.toActor(user));
  }

  @ReadData
  @ApiOperation(value = "删除职务")
  @DeleteMapping("{userId}/actors/{id}")
  public void delete(@PathVariable(value = "userId") Worker user,
                     @PathVariable(value = "id") Actor actor) {
    actorService.delete(actor);
  }

  @ReadData
  @ApiOperation(value = "设置默认职务")
  @PatchMapping("default-actor/{id}")
  public void setDefault(@PathVariable(value = "id") Actor actor) {
    actorService.setDefault(actor);
  }
}
