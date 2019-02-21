package com.github.jnoee.xo.starter.demo.visit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
  @NotBlank
  @ApiModelProperty(value = "手机", required = true, example = "13800138000")
  private String phone;
  @NotBlank
  @ApiModelProperty(value = "密码", required = true, example = "666666")
  private String password;
}
