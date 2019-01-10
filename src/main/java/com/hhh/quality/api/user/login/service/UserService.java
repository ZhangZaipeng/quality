package com.hhh.quality.api.user.login.service;


import com.hhh.quality.platform.response.ResponseModel;
import com.hhh.quality.api.user.login.domain.User;
import com.hhh.quality.api.user.login.domain.UserAgent;
import com.hhh.quality.api.user.login.domain.vo.RegisterVo;
import com.hhh.quality.api.user.login.domain.vo.ResetPwdVo;

/**
 * 用户----相关接口
 */
public interface UserService {


  /**
   * 添加用户信息
   * @param user
   * @param userAgent
   * @return
   */
  boolean regUser(User user, UserAgent userAgent);

  /**
   * 用户注册
   * @param registerVo 注册VO
   * @return
   */
  ResponseModel register(RegisterVo registerVo);

  /**
   * 重置密码
   * @param resetPwdVo
   * @return
   */
  ResponseModel resetPwd(ResetPwdVo resetPwdVo);

  /**
   * 点击获取注册验证码
   */
  ResponseModel clickGetMsgVerifyCode(String account, String type, String token);

  /**
   * 验证密码一致性
   * @param password 密码
   * @param rePassword 二次输入密码
   * @return
   */
  boolean checkRegisterOrResetPwd(String password, String rePassword);

  /**
   * 验证手机or邮箱验证码
   * @param account  账号
   * @param verifyCode 输入验证码
   * @param codeKey
   * @return
   */
  boolean  checkMsgCode(String account, String verifyCode, String codeKey);

}
