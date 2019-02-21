package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.starter.demo.core.entity.work.Role;
import com.github.jnoee.xo.utils.VoUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleVo {
  @ApiModelProperty(value = "ID")
  private String id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "权限")
  private List<String> privilegs = new ArrayList<>();

  public static RoleVo forView(Role role) {
    return VoUtils.copy(role, RoleVo.class);
  }

  public static List<RoleVo> forList(List<Role> roles) {
    return VoUtils.copy(roles, RoleVo.class, "privilegs");
  }
}
