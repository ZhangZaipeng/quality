package com.hhh.quality.platform.exception;

import com.hhh.quality.platform.response.ResponseCode;

public class ResultErrException extends RuntimeException {

  private int errCode;
  private String errMsg;

  public ResultErrException(ResponseCode errCode, String errMsg) {
    super(errMsg);
    this.errCode = errCode.getCode();
  }

  public ResultErrException(ResponseCode responseCode) {
    super(responseCode.getMsg());
    this.errCode = responseCode.getCode();
  }

  public ResultErrException(int errCode, String errMsg) {
    super(errMsg);
    this.errCode = errCode;
  }

  public ResultErrException(String errMsg) {
    super(errMsg);
    this.errCode = ResponseCode.FAIL.getCode();
  }

  public int getErrCode() {
    return errCode;
  }
}
