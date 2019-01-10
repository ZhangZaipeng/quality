package com.hhh.quality.api.user.login.controller;


import com.estate.api.config.i18n.I18nUtils;
import com.estate.api.user.login.domain.LoginVo;
import com.estate.api.user.login.service.LoginService;
import com.estate.common.StringUtils;
import com.estate.platform.response.ResponseCode;
import com.estate.platform.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/login")
@Api(value="/login", description="用户登录相关接口", position = 2)
public class UserLoginController {

  @Autowired
  private LoginService loginService;

  @Autowired
  private I18nUtils i18nUtils;

  @ApiOperation(value = "登录", httpMethod = "POST", notes = "登录", position = 5)
  @PostMapping(value = "/login.json")
  public ResponseModel login(@RequestBody LoginVo loginVo) {

    // 参数校验
    String loginName = loginVo.getLoginName();
    if (StringUtils.isNullOrEmpty(loginName)) {
      return ResponseModel.getModel(ResponseCode.FAIL,"账号不能为空");
    }

    String  password = loginVo.getPassword();
    if (StringUtils.isNullOrEmpty(password)) {
      return ResponseModel.getModel(ResponseCode.FAIL,"密码不能为空");
    }

    return loginService.login(loginVo.getLoginName().trim(),loginVo.getPassword());
  }

  @ApiOperation(value = "退出登录", httpMethod = "GET", notes = "退出登录", position = 5)
  @RequestMapping(value = "/loginOut.json", method = RequestMethod.GET)
  public ResponseModel loginOut() {
    return loginService.loginOut();
  }

}
