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

  @ApiModelProperty(value = "昵称")
  private String nickName;

  @ApiModelProperty(value = "头像")
  private String imgUrl;

  @ApiModelProperty(value = "权限列表", example = "[]")
  private List<String> privilegs = new ArrayList<>();

  public LoginVo(Visitor user, List<String> privilegs) {
    id = user.getId();
    nickName = user.getNickName();
    imgUrl = user.getNickName();
    this.privilegs = privilegs;
  }
}
