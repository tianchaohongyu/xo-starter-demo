package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.ienum.vo.EnumVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorVo {

  @ApiModelProperty(value = "ID")
  private String id;

  @ApiModelProperty(value = "昵称")
  private String nickName;

  @ApiModelProperty(value = "头像")
  private String imgUrl;

  @ApiModelProperty(value = "手机")
  private String mobile;

  @ApiModelProperty(value = "身份")
  private IdentityVo identity;

  @ApiModelProperty(value = "启用状态")
  private EnumVo status;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;
}
