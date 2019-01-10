package com.hhh.quality.api.user.login.service;


import com.hhh.quality.platform.response.ResponseModel;

/**
 * 用户登陆-- 相关接口
 */
public interface LoginService {

  /**
   *  登录接口
   * @param account
   * @param pwd
   * @return
   */
  ResponseModel login(String account, String pwd);

  /**
   * 退出登录
   * @return
   */
  ResponseModel loginOut();
}
