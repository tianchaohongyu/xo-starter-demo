package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.model.Page;
import com.github.jnoee.xo.starter.demo.core.entity.work.Worker;
import com.github.jnoee.xo.starter.demo.core.enums.EnabledStatus;
import com.github.jnoee.xo.utils.VoUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WorkerVo {
  @ApiModelProperty(value = "ID")
  private String id;

  @ApiModelProperty(value = "工作人员名")
  private String username;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "默认职务")
  private String defaultActor;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "修改时间")
  private Date updateTime;

  @ApiModelProperty(value = "修改人")
  private String updateUser;

  @ApiModelProperty(value = "启用状态")
  private EnabledStatus status;

  @ApiModelProperty(value = "序号")
  private Integer ordinal;

  public static WorkerVo forView(Worker user) {
    WorkerVo vo = VoUtils.copy(user, WorkerVo.class);
    vo.createUser = user.getCreateUser().getName();
    vo.updateUser = user.getUpdateUser().getName();
    vo.defaultActor = user.getDefaultActor().getFullName();
    return vo;
  }

  public static Page<WorkerVo> forPage(Page<Worker> userPage) {
    Page<WorkerVo> voPage = new Page<>(userPage.getCount(), userPage.getNumber(), userPage.getSize());
    for (Worker user : userPage.getContents()) {
      voPage.getContents().add(WorkerVo.forView(user));
    }
    return voPage;
  }
}
