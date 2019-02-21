package com.github.jnoee.xo.starter.demo.work.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
  @NotBlank
  @ApiModelProperty(value = "工作人员名", required = true, example = "admin")
  private String username;
  @NotBlank
  @ApiModelProperty(value = "密码", required = true, example = "admin")
  private String password;
}
