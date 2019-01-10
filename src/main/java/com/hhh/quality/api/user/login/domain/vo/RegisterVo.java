package com.hhh.quality.api.user.login.domain.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户注册----VO
 */
public class RegisterVo {

  @ApiModelProperty(value = "手机号码")
  private String account;

  @ApiModelProperty(value = "密码")
  private String password;

  @ApiModelProperty(value = "重复密码")
  private String rePassword;

  @ApiModelProperty(value = "短信验证码")
  private String verifyCode;

  @ApiModelProperty(value = "邀请码")
  private String inviteCode;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRePassword() {
    return rePassword;
  }

  public void setRePassword(String rePassword) {
    this.rePassword = rePassword;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public void setInviteCode(String inviteMobile) {
    this.inviteCode = inviteMobile;
  }
}
