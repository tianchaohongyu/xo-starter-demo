package com.github.jnoee.xo.starter.demo.dto;

import com.github.jnoee.xo.starter.demo.entity.work.Actor;
import com.github.jnoee.xo.starter.demo.entity.work.Organ;
import com.github.jnoee.xo.starter.demo.entity.work.Role;
import com.github.jnoee.xo.starter.demo.entity.work.Worker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserAddDto {
  @NotBlank
  @ApiModelProperty(value = "工作人员名", required = true, example = "test001")
  private String username;
  @NotBlank
  @ApiModelProperty(value = "姓名", required = true, example = "测试工作人员")
  private String name;
  @NotNull
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;
  @NotBlank
  @ApiModelProperty(value = "机构ID", required = true,
      example = "ADMINOID-0000-0000-0000-000000000000")
  private String organId;
  @NotBlank
  @ApiModelProperty(value = "角色ID", required = true,
      example = "ADMINRID-0000-0000-0000-000000000000")
  private String roleId;
  @NotBlank
  @ApiModelProperty(value = "职务名称", required = true, example = "测试组长")
  private String actorName;

  public Worker toUser() {
    Worker user = new Worker();
    user.setUsername(username);
    user.setName(name);
    user.setOrdinal(ordinal);

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
