package com.github.jnoee.xo.starter.demo.work.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActorEditDto extends ActorAddDto {
  @NotBlank
  @ApiModelProperty(value = "ID", required = true)
  private String id;
}
