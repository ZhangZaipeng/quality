package com.hhh.quality.api.user.login.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResetPwdVo {
  @ApiModelProperty(value = "手机/邮箱")
  private String account; // 手机号码

  @ApiModelProperty(value = "密码")
  private String password; // 当登录密码有值时,这里表示资金安全码

  @ApiModelProperty(value = "重复密码")
  private String rePassword; // 当登录密码有值时,这里表示资金安全码

  @ApiModelProperty(value = "短信验证码")
  private String verifyCode;

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

}
