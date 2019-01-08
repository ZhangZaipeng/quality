/**
 * @(#)Principal.java Copyright 2011 jointown, Inc. All rights reserved.
 */
package com.hhh.quality.platform.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 * 此接口表示需要认证主体的抽象概念，它可以用来表示任何实体
 *
 * @author jianguo.xu
 * @version 1.0, 2011-2-17
 */
public interface Principal {

  /**
   * 得到用户标识
   *
   * @return 用户标识
   */
  @JsonIgnore
  public Serializable getIdentity();

  /**
   * 得到用户最后登录时间
   *
   * @return 用户最后登录时间 （返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此日期表示的毫秒数）
   */
  public Long getLastLoginTime();

  /**
   * 得到登录用户名
   *
   * @return 用户名
   */
  public String getLoginName();

  /**
   * 得到用户编号
   */
  public Long getUserId();

  /**
   * 得到用户手机号
   */
  public String getTelephone();

  /**
   * 得到用户邮箱号
   */
  public String getEmail();
}
