package com.github.jnoee.xo.starter.demo.dto;

import com.github.jnoee.xo.starter.demo.entity.work.Actor;
import com.github.jnoee.xo.starter.demo.entity.work.Organ;
import com.github.jnoee.xo.starter.demo.entity.work.Role;
import com.github.jnoee.xo.starter.demo.entity.work.Worker;
import com.github.jnoee.xo.utils.VoUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ActorAddDto {
  @NotBlank
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "机构ID")
  private String organId;
  @ApiModelProperty(value = "角色ID")
  private String roleId;

  public Actor toActor(Worker user) {
    Actor actor = VoUtils.copy(this, Actor.class);
    actor.setUser(user);

    Organ organ = new Organ();
    organ.setId(organId);
    actor.setOrgan(organ);

    Role role = new Role();
    role.setId(roleId);
    actor.setRole(role);

    return actor;
  }
}
