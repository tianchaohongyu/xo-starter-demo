package com.github.jnoee.xo.starter.demo.work.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class VisitorAddDto {

  @NotBlank
  @ApiModelProperty(value = "昵称", required = true)
  private String nickName;

  @NotNull
  @ApiModelProperty(value = "身份id", required = true)
  private String identityId;

  @Pattern(regexp = "1\\d{10}")
  @ApiModelProperty(value = "手机", required = true)
  private String mobile;

  @ApiModelProperty(value = "密码", required = true)
  private String password;

  @ApiModelProperty(value = "头像", required = true)
  private String imgUrl;
}
