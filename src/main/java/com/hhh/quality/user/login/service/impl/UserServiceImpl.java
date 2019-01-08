package com.hhh.quality.user.login.service.impl;

import com.estate.api.basic.domain.User;
import com.estate.api.basic.domain.UserAgent;
import com.estate.api.basic.mapper.UserAgentMapper;
import com.estate.api.basic.mapper.UserMapper;
import com.estate.api.config.i18n.I18nUtils;
import com.estate.api.user.SendTeleEmailUtils;
import com.estate.api.user.login.domain.RegisterVo;
import com.estate.api.user.login.domain.ResetPwdVo;
import com.estate.api.user.login.service.LoginService;
import com.estate.api.user.login.service.UserService;
import com.estate.common.AESUtils;
import com.estate.common.BCrypt;
import com.estate.common.InviteCodeUtil;
import com.estate.common.RandomUtils;
import com.estate.common.StringUtils;
import com.estate.common.VerifyCodeUtils;
import com.estate.common.enums.VerifyCodeEnum;
import com.estate.platform.exception.ResultErrException;
import com.estate.platform.redis.RedisFactory;
import com.estate.platform.response.ResponseCode;
import com.estate.platform.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private RedisFactory redisFactory;

  @Autowired
  private UserAgentMapper userAgentMapper;


  @Autowired
  private UserMapper userMapper;

  @Autowired
  private LoginService loginService;

  @Autowired
  private I18nUtils i18nUtils;

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean regUser(User user, UserAgent userAgent) {
   try{

     int r = userMapper.insertSelective(user);
     userAgent.setUserId(user.getUserId());
     int r1 = userAgentMapper.insertSelective(userAgent);


     boolean flag = (r == 1 && r1 == 1 );

     log.info("UserServiceImpl 是否注册成功:" + flag);

     return flag;
   }catch (Exception e){
     e.printStackTrace();
     log.error("UserServiceImpl 添加用户信息错误："+e.getMessage());
   }
    return false;
  }


  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public ResponseModel register(RegisterVo registerVo) {

    String account = registerVo.getAccount(); // 手机
    String password = AESUtils.AESDecode(AESUtils.KEY,registerVo.getPassword()); // 密码
    String rePassword = AESUtils.AESDecode(AESUtils.KEY, registerVo.getRePassword()); // 重复密码
    String verifyCode = registerVo.getVerifyCode(); // 验证码
    String inviteCode = registerVo.getInviteCode();

    // 手机号码验证
    if (!VerifyCodeUtils.checkTeleTrue(account)) { // 不是手机号码
      throw new ResultErrException("手机号码错误");
    }

    // 判断用户是否注册
    UserAgent userAgent = userAgentMapper.selectByLoginName(account);
    if (userAgent != null) {
      throw new ResultErrException("用户已经存在");
    }

    // 判断用户邀请码是否有效
    Long inviteUserId =0l;
    if(!StringUtils.isNullOrEmpty(inviteCode)){
      try {
        inviteUserId = InviteCodeUtil.codeToId(inviteCode);
        User inviteUser = userMapper.findUserById(inviteUserId);
        if(inviteUser == null){
          throw new ResultErrException(i18nUtils.getMessage("mess.invite.code.error"));
        }
      }catch (NumberFormatException e){
        throw new ResultErrException(i18nUtils.getMessage("mess.invite.code.error"));
      }
    }

    // 密码一致性验证
    checkRegisterOrResetPwd(password, rePassword);

    // 短信/邮箱验证码验证
    String codeKey = VerifyCodeEnum.getCodeKey(1000);
    checkMsgCode(account, verifyCode, codeKey);

    // 注册用户
    User user = new User();

    UserAgent regUserAgent = new UserAgent();
    regUserAgent.setLoginName(account);
    regUserAgent.setTelephone(account);
    String cryptString = BCrypt.hashpw(password, BCrypt.gensalt());
    regUserAgent.setLoginPwd(cryptString);
    regUserAgent.setDeleted((short)1);
    regUserAgent.setLoginCount(0);

    user.setMobile(account);
    user.setInviteesUserId(new Long(inviteUserId));

    this.regUser(user, regUserAgent);

    // 完成登录
    ResponseModel responseModel = loginService.login(account, registerVo.getPassword());

    return ResponseModel.getModel(ResponseCode.SUCCESS, responseModel);
  }


  @Override
  public ResponseModel clickGetMsgVerifyCode(String account, String type, String token) {

    // type
    if (StringUtils.isNullOrEmpty(type)) { // 空 或 不为1  或 不为2
      return ResponseModel.getModel(ResponseCode.FAIL,"type 不为空");
    }

    // 验证手机号码  合法
    if (!VerifyCodeUtils.checkTeleTrue(account)) {
      return ResponseModel.getModel(ResponseCode.FAIL, "手机号码  不合法");
    }

    // account + type
    String r = AESUtils.AESDecode(AESUtils.KEY, token);
    if (!(account + type).equals(r)) {
      return ResponseModel.getModel(ResponseCode.FAIL, "token 检验错误");
    }

    String codeKey = VerifyCodeEnum.getCodeKey(Integer.parseInt(type));

    // 成功标识
    boolean sendMsgFlag = false;
    String[] responseStr = {""};

    // 获取 code
    String code = RandomUtils.runVerifyCode(6);

    // 短信过期时间 5分钟
    Long time = redisFactory.ttl(codeKey + ":" + account);
    if(time > 240){
      throw new ResultErrException("1分钟内请勿重复发送验证码");
    }
    redisFactory.setex(codeKey + ":" + account, 60 * 5, code);

    // 发送短信
    sendMsgFlag = SendTeleEmailUtils.sendTelephone(responseStr,code,account,Integer.parseInt(type));

    // 返回数据
    if (sendMsgFlag) {
      return ResponseModel.getModel(ResponseCode.SUCCESS, responseStr[0]);
    }

    return ResponseModel.getModel(ResponseCode.FAIL, "发送失败");
  }

  @Override
  public boolean checkRegisterOrResetPwd(String password, String rePassword) {
    /*if (!VerifyCodeUtils.checkPwd(password)) {
      throw new ResultErrException("密码太简单");
    }*/
    // 两次密码是否相同
    if (!password.equals(rePassword)) {
      throw new ResultErrException("两次输入密码不同");
    }
    return true;
  }

  @Override
  public boolean checkMsgCode(String account, String verifyCode,String codeKey){
    if (StringUtils.isNullOrEmpty(verifyCode)) {
      throw new ResultErrException("验证码不能为空");
    }
    String redisVfCode = redisFactory.get(codeKey + ":" + account);

    if (StringUtils.isNullOrEmpty(redisVfCode)) {
      throw new ResultErrException("验证码已过期");
    }

    if (!verifyCode.toLowerCase().equals(redisVfCode.toLowerCase())) { // redis中验证码  与 传来的验证码不一致
      throw new ResultErrException("验证码错误");
    }
    return true;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public ResponseModel resetPwd(ResetPwdVo resetPwdVo) {

    String account = resetPwdVo.getAccount(); // 账号
    String password = AESUtils.AESDecode(AESUtils.KEY, resetPwdVo.getPassword()); // 密码
    String rePassword = AESUtils.AESDecode(AESUtils.KEY, resetPwdVo.getRePassword()); // 重复密码
    String verifyCode = resetPwdVo.getVerifyCode(); // 短信验证码

    // 根据类型检验账号是否合法
    // 手机号码验证
    if (!VerifyCodeUtils.checkTeleTrue(account)) { // 不是手机号码
      throw new ResultErrException("号码不合法");
    }

    // 校验是否已经注册
    UserAgent userAgent = userAgentMapper.selectByLoginName(account);
    if (userAgent == null) {
      return ResponseModel.getModel(ResponseCode.FAIL, "用户未注册");
    }

    // 校验密码
    checkRegisterOrResetPwd(password,rePassword);

    // 短信/邮箱验证
    String codeKey = VerifyCodeEnum.getCodeKey(2000);
    checkMsgCode(account, verifyCode, codeKey);

    // 重置密码
    String cryptString = BCrypt.hashpw(password, BCrypt.gensalt());
    userAgentMapper.resetLoginPwd(userAgent.getUserId(), cryptString);

    // 完成登录
    ResponseModel responseModel = loginService.login(account, resetPwdVo.getPassword());

    return responseModel;
  }
}
