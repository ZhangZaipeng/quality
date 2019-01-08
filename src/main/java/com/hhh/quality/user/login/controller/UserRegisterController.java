package com.hhh.quality.user.login.controller;

import com.estate.api.user.login.domain.RegisterVo;
import com.estate.api.user.login.domain.ResetPwdVo;
import com.estate.api.user.login.service.UserService;
import com.estate.platform.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user/register")
@Api(value="/register", description="用户注册相关接口", position = 1)
public class UserRegisterController {

  @Autowired
  private UserService userService;

  /**
   * 点击发送手机
   * @param account 账号信息
   * @param type  类型
   * @return
   */
  @ApiOperation(value = "点击发送验证码", httpMethod = "GET",
      notes = "点击发送验证码,type有以下情况:1000 注册验证码,2000 找回验证码,4000 修改用户登陆密码或资金密码")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "account", value = "手机号码", required = true, paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "type", value = "1000 注册验证码,2000  找回验证码,4000 修改用户登陆密码或资金密码",
          required = true, paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "token", value = "口令",
          required = true, paramType = "query", dataType = "string"),
  })
  @RequestMapping(value = "/sendMsgVerifyCode.json", method = RequestMethod.GET)
  public ResponseModel clickGetMsgVerifyCode(@RequestParam(required = true) String account,
      @RequestParam(required = true) String type,@RequestParam(required = true) String token) {
    return userService.clickGetMsgVerifyCode(account, type, token);
  }


  /**
   * 注册新用户
   * @param registerVo  注册VO
   * @return
   */
  @ApiOperation(value = "注册(手机号码)", httpMethod = "POST", notes = "注册(手机号码)", position = 5)
  @RequestMapping(value = "/account.json", method = RequestMethod.POST)
  public ResponseModel registerMobile(@RequestBody RegisterVo registerVo) {
    return userService.register(registerVo);
  }

  /**
   * 找回重置密码(手机号码/邮箱)
   */
  @ApiOperation(value = "重置密码(手机号码)", httpMethod = "POST", notes = "重置密码(手机号码)", position = 5)
  @RequestMapping(value = "/resetPwd.json", method = RequestMethod.POST)
  public ResponseModel resetPwd(@RequestBody ResetPwdVo resetPwdVo) {
    return userService.resetPwd(resetPwdVo);
  }
}
