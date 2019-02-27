package com.github.jnoee.xo.starter.demo.work.dto;

import com.github.jnoee.xo.starter.demo.core.entity.work.Actor;
import com.github.jnoee.xo.starter.demo.core.entity.work.Organ;
import com.github.jnoee.xo.starter.demo.core.entity.work.Role;
import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WorkerDto {
  @NotBlank(message = "工作人员名不能为空")
  @ApiModelProperty(value = "工作人员名", required = true)
  private String username;

  @NotBlank(message = "姓名不能为空")
  @ApiModelProperty(value = "姓名", required = true)
  private String name;

  @NotBlank(message = "机构ID不能为空")
  @ApiModelProperty(value = "机构ID", required = true)
  private String organId;

  @NotBlank(message = "角色ID不能为空")
  @ApiModelProperty(value = "角色ID", required = true)
  private String roleId;

  @NotBlank(message = "职务名称不能为空")
  @ApiModelProperty(value = "职务名称", required = true)
  private String actorName;

  public Worker toUser() {
    Worker user = new Worker();
    user.setUsername(username);
    user.setName(name);

    Organ organ = new Organ();
    organ.setId(organId);
    Role role = new Role();
    role.setId(roleId);

    Actor actor = new Actor();
    actor.setName(actorName);
    actor.setOrgan(organ);
    actor.setRole(role);
    user.setDefaultActor(actor);
    return user;
  }
}
