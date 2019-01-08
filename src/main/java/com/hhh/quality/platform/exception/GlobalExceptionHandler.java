package com.hhh.quality.platform.exception;

import com.hhh.quality.platform.login.AuthenticationException;
import com.hhh.quality.platform.login.LoginErrException;
import com.hhh.quality.platform.response.ResponseCode;
import com.hhh.quality.platform.response.ResponseModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({
      ResultErrException.class, LoginErrException.class, AuthenticationException.class,
  })
  @ResponseBody
  public ResponseModel resolveException(Exception ex) {
    ResponseModel model = ResponseModel.error(ResponseCode.API_99999).setMsg("网络不给力呀,请稍后重试!");
    if (ex instanceof ResultErrException) {

      model = ResponseModel.getModel(ResponseCode.FAIL);

      model.setMsg(ex.getMessage());

    } else if (ex instanceof LoginErrException) {

      model = ResponseModel.getModel(ResponseCode.USER_10000);

      model.setMsg(ResponseCode.USER_10000.getMsg());

    } else if (ex instanceof AuthenticationException) {

      model = ResponseModel.getModel(ResponseCode.FAIL);

      model.setMsg(ex.getMessage());

    } else {
      // 打印堆栈日志到日志文件中
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      ex.printStackTrace(new java.io.PrintWriter(buf, true));
      String  expMessage = buf.toString();
      try {
        buf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      logger.error("GlobalExceptionHandler,捕获异常:"+ ex.getMessage() + "; eString:" + expMessage);
    }

    return model;
  }
}
