package com.github.jnoee.xo.starter.demo.enums;


import com.github.jnoee.xo.ienum.IEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 访客身份类型。
 */
public enum VisitorType implements IEnum {

  /** 普通用户 */
  DEFAULT("普通用户", "0", "default:default"),

  /** 企业用户 */
  COMPANY("企业用户", "1", "company:default");

  @Getter
  private String text;
  @Getter
  private String value;

  private List<String> privilegs;

  /**
   * 构造方法
   *
   * @param text  文本
   * @param value 值
   */
  private VisitorType(String text, String value, String... privilegs) {
    this.text = text;
    this.value = value;
    this.privilegs = Arrays.asList(privilegs);
  }

  @Override
  public String toString() {
    return text;
  }
}
