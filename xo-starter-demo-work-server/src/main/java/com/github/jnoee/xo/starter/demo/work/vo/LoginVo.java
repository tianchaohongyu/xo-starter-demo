package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
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
  @ApiModelProperty(value = "机构名称")
  private String organName;
  @ApiModelProperty(value = "角色名称")
  private String roleName;
  @ApiModelProperty(value = "权限列表")
  private List<String> privilegs = new ArrayList<>();

  public LoginVo(Worker user, List<String> privilegs) {
    id = user.getId();
    name = user.getName();
    organName = user.getDefaultActor().getOrgan().getName();
    roleName = user.getDefaultActor().getRole().getName();
    this.privilegs = privilegs;
  }
}
