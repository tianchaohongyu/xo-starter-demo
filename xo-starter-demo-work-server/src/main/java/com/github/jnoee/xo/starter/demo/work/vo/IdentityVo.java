package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.starter.demo.core.enums.IdentityType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdentityVo {
  @ApiModelProperty(value = "ID")
  private String id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "代码")
  private String code;

  @ApiModelProperty(value = "类型")
  private IdentityType type;
}
