package com.github.jnoee.xo.starter.demo.work.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VisitorEditDto extends VisitorAddDto {

  @NotBlank
  @ApiModelProperty(value = "ID", required = true)
  private String id;
}
