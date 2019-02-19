package com.github.jnoee.xo.starter.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrganEditDto extends OrganAddDto {
  @NotBlank
  @ApiModelProperty(value = "ID", required = true)
  private String id;
}
