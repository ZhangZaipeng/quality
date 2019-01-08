package com.hhh.quality.platform.response;

/**
 * 错误码文档
 */

public enum ResponseCode {

  // 公共
  API_99999(9999, "操作异常，请重新尝试"),
  API_00000(200, "操作成功"),
  API_00200(200, "操作成功"),
  API_OK(200, "操作成功"),
  SUCCESS(200, "操作成功"),
  FAIL(400, "fail"),

  // ======== user ===============
  USER_10000(10000, "用户未登录"),
  USER_10001(10001, "注册失败"),
  USER_10002(10002, "用户验证码在有效期"),
  USER_10003(10003, "用户验证码无效,请重新获取"),
  USER_10004(10004, "用户未设置资金安全密码"),
  USER_10005(10005, "用户未认证"),

  // ======== user end ===========

  ;
  private int code;
  private String msg;

  ResponseCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ResponseCode getByCode(int code) {
    for (ResponseCode ec : ResponseCode.values()) {
      if (ec.getCode() == code) {
        return ec;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
