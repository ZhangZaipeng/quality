package com.hhh.quality.user.login.service.impl;

import com.estate.api.basic.mapper.UserAgentMapper;
import com.estate.api.config.i18n.I18nUtils;
import com.estate.api.user.login.AppLoginVerifier;
import com.estate.api.user.login.service.LoginService;
import com.estate.common.AESUtils;
import com.estate.platform.login.IdentityValidator;
import com.estate.platform.login.Principal;
import com.estate.platform.response.ResponseCode;
import com.estate.platform.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IdentityValidator identityValidator;

  @Override
  public ResponseModel login(String account, String pwd) {

    // 解密
    String password = AESUtils.AESDecode(AESUtils.KEY, pwd);

    // 登录
    Principal principal = identityValidator.login(new AppLoginVerifier(account, password));

    // Long userId = principal.getUserId();
    // User user = userMapper.findUserById(userId);

    // Map<String, Object> r = new HashMap<String, Object>();

    return ResponseModel.getModel(ResponseCode.SUCCESS);
  }

  @Override
  public ResponseModel loginOut() {
    // 清除cookie
    identityValidator.logout();
    return ResponseModel.getModel(ResponseCode.SUCCESS);
  }

}
