package com.github.jnoee.xo.starter.demo.work.dto;

import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import com.github.jnoee.xo.utils.VoUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WorkerEditDto {
  @NotBlank
  @ApiModelProperty(value = "工作人员ID", required = true)
  private String id;

  @NotBlank
  @ApiModelProperty(value = "工作人员名", required = true)
  private String username;

  @NotBlank
  @ApiModelProperty(value = "姓名", required = true)
  private String name;

  @NotNull
  @Range(min = 1, max = 999)
  @ApiModelProperty(value = "序号", required = true, example = "999")
  private Integer ordinal;

  public Worker toUser() {
    Worker worker = VoUtils.copy(this, Worker.class);
    worker.setStatus(null);
    return worker;
  }
}
