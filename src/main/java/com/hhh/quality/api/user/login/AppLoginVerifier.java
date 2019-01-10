package com.hhh.quality.api.user.login;

import com.hhh.quality.platform.login.impl.PasswordVerifier;

/**
 * 移动端 ：登录名 + 登录密码
 */
public class AppLoginVerifier extends PasswordVerifier {

  public AppLoginVerifier(String loginName, String password) {
    super(loginName, password);
  }

}
