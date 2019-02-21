package com.github.jnoee.xo.starter.demo.visit.vo;

import com.github.jnoee.xo.starter.demo.core.entity.visit.Visitor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginVo {
  @ApiModelProperty(value = "ID")
  private String id;
  @ApiModelProperty(value = "姓名")
  private String name;
  @ApiModelProperty(value = "角色名称")
  private String roleName;
  @ApiModelProperty(value = "权限列表", example = "[]")
  private List<String> privilegs = new ArrayList<>();

  public LoginVo(Visitor user, List<String> privilegs) {
    id = user.getId();
    name = user.getNickName();
    this.privilegs = privilegs;
  }
}
