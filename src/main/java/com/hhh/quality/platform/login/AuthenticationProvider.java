/**
 * @(#)AuthenticationProvider.java Copyright 2011 jointown, Inc. All rights reserved.
 */
package com.hhh.quality.platform.login;

import java.io.Serializable;
import java.util.Date;

/**
 * 身份验证提供者接口
 *
 * @author jianguo.xu
 * @version 1.0, 2011-2-17
 */
public interface AuthenticationProvider {

  /**
   * 根据用户标识返回认证用户
   *
   * @return 认证用户 没有返回null
   */
  public Principal get(String uuid);

  /**
   * 清空 用户标识 uuid
   */
  public Long rm(String uuid);

  /**
   * 验证用户
   */
  public Principal authenticate(Verifier verifier) throws AuthenticationException;

  /**
   * 验证用户
   */
  public Principal authenticate(Verifier verifier, String ip) throws AuthenticationException;

  /**
   * 得到最后操作时间
   */
  public Date getLastRequestTime(String uuid);

  /**
   * 设置最后操作时间
   */
  public void setLastRequestTime(Date lastRequestTime, Serializable id, String uuid);

  public Principal authenticate_auto(Verifier verifier) throws AuthenticationException;

}
