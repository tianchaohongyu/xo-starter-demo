package com.github.jnoee.xo.starter.demo.work.dto;

import com.github.jnoee.xo.starter.demo.core.enums.IdentityType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IdentityAddDto {
  @NotBlank
  @ApiModelProperty(value = "名称", required = true)
  private String name;

  @NotBlank
  @ApiModelProperty(value = "代码", required = true)
  private String code;

  @NotNull
  @ApiModelProperty(value = "类型", required = true)
  private IdentityType type;
}
