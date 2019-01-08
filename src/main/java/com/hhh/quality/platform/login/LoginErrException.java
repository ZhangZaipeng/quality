package com.hhh.quality.platform.login;

import com.hhh.quality.platform.response.ResponseCode;

public class LoginErrException extends RuntimeException {

  private ResponseCode code;
  public LoginErrException() {
    super();
  }

  public LoginErrException(String message, Throwable cause) {
    super(message, cause);
  }

  public LoginErrException(ResponseCode code,String message) {
    super(message);
    this.code = code;
  }

  public ResponseCode getCode() {
    return code;
  }

  public LoginErrException(Throwable cause) {
    super(cause);
  }
}
