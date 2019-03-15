package com.github.jnoee.xo.starter.demo.work.vo;

import com.github.jnoee.xo.ienum.vo.EnumVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class IdentityVo {
  @ApiModelProperty(value = "ID")
  private String id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "代码")
  private String code;

  @ApiModelProperty(value = "类型")
  private EnumVo type;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "创建人")
  private String createUser;

  @ApiModelProperty(value = "修改时间")
  private Date updateTime;

  @ApiModelProperty(value = "修改人")
  private String updateUser;

  public IdentityVo(){

  }

  /**
   * 构造身份vo
   * @param id id
   * @param name 名称
   */
  public IdentityVo(String id, String name) {
    setId(id);
    setName(name);
  }
}
