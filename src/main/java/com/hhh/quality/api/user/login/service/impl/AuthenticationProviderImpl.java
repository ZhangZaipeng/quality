package com.hhh.quality.api.user.login.service.impl;


import com.hhh.quality.common.BCrypt;
import com.hhh.quality.common.DateUtils;
import com.hhh.quality.common.StringUtils;
import com.hhh.quality.platform.Conv;
import com.hhh.quality.platform.YvanUtil;
import com.hhh.quality.platform.exception.ResultErrException;
import com.hhh.quality.platform.login.AuthenticationException;
import com.hhh.quality.platform.login.AuthenticationProvider;
import com.hhh.quality.platform.login.Principal;
import com.hhh.quality.platform.login.Verifier;
import com.hhh.quality.platform.redis.RedisFactory;
import com.hhh.quality.api.user.login.AppLoginVerifier;
import com.hhh.quality.api.user.login.domain.UserAgent;
import com.hhh.quality.api.user.login.mapper.UserAgentMapper;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

  Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

  private static final String USER_CACHE = "USER_AGENT";
  private static final String LOGIN_TOKEN = "LOGIN_TOKEN";


  @Autowired
  private RedisFactory redisFactory;

  @Autowired
  private UserAgentMapper userAgentMapper;

  @Override
  public Principal get(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = redisFactory.get(cacheKey);
    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }
    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);
    String ids = map.get("id").toString();
    if (ids.startsWith("USER:")) {

      UserAgent userAgent = getUserAgentFromCache(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        return userAgent;
      }
      userAgent = userAgentMapper.selectByPrimaryKey(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        addToCache(userAgent);
        return userAgent;
      }
    }
    return null;
  }

  @Override
  public Long rm(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;
    return redisFactory.del(cacheKey);
  }

  @Override
  public Principal authenticate(Verifier verifier) throws AuthenticationException {

    if (verifier instanceof AppLoginVerifier) {
      // 前段
      AppLoginVerifier appLoginVerifier = (AppLoginVerifier) verifier;

      UserAgent userAgent = userAgentMapper.selectByLoginName(appLoginVerifier.getLoginName());

      if (userAgent == null) {
        throw new AuthenticationException("用户不存在");
      }
      // -1-冻结, 0-未激活, 1-正常, 9-异常
      userStatusCheck(userAgent);

      if (!BCrypt.checkpw(appLoginVerifier.getPassword(), userAgent.getLoginPwd())) {
        throw new AuthenticationException("密码不正确");
      }

      // lastLoginTime
      Long lastLoginTime = System.currentTimeMillis() / 1000;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      // 更新登录次数 最后登录时间
      userAgentMapper.loginSuccess(userAgent.getUserAgentId(),
          DateUtils.format(new Date(lastLoginTime*1000),"yyyy-MM-dd HH:mm:ss"));
      userAgent.setLastLoginTime(lastLoginTime);

      addToCache(userAgent);

      userAgent.setLoginName(appLoginVerifier.getLoginName());

      logger.info(">>>>> 用户 {} >>>>> 在时间 {} >>>>> 密码登录校验成功", userAgent.getUserId(), lastLoginTime );

      return userAgent;

    }

    throw new ResultErrException("mess.user.logintype.error");
  }

  @Override
  public Principal authenticate(Verifier verifier, String ip) throws AuthenticationException {
    // 暂不支持
    throw new ResultErrException("mess.user.login.notsupport");
  }

  @Override
  public Date getLastRequestTime(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = redisFactory.get(cacheKey);

    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }

    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);

    Long lastRequestTime = Conv.NL(map.get("lastRequestTime"));

    return new Date(lastRequestTime);
  }

  @Override
  public void setLastRequestTime(Date lastRequestTime, Serializable id, String uuid) {

    Map<String, Object> map = new HashMap<String, Object>();
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    map.put("id", id);
    map.put("lastRequestTime", Conv.NS(lastRequestTime.getTime()));

    String ids = id.toString();
    int expireSec = 0;
    if (ids.startsWith("USER:")) {  // 1个小时
      // expireSec = Integer.MAX_VALUE;
      expireSec = 12 * 60 * 60 ;
    }

    String jsonValue = YvanUtil.toJson(map);

    redisFactory.setex(cacheKey, expireSec, jsonValue);

  }

  @Override
  public Principal authenticate_auto(Verifier verifier) throws AuthenticationException {

    throw new ResultErrException("不支持的登录类型！");
  }

  private void addToCache(UserAgent userAgent) {
    String cacheKey = USER_CACHE + ":" + userAgent.getUserAgentId();

    String jsonValue = YvanUtil.toJson(userAgent);
    // 存放到redis中
    redisFactory.setex(cacheKey, 120, jsonValue);

  }

  private UserAgent getUserAgentFromCache(Long custAgentId) {
    String cacheKey = USER_CACHE + ":" + custAgentId;

    String jv = redisFactory.get(cacheKey);

    if (!StringUtils.isNullOrEmpty(jv)) {
      return YvanUtil.jsonToObj(jv, UserAgent.class);
    }

    return null;
  }

  private void userStatusCheck(UserAgent userAgent){
    if (userAgent.getDeleted() == -1) {
      throw new AuthenticationException("帐号已被冻结");
    }

    if (userAgent.getDeleted() == 0) {
      throw new AuthenticationException("未激活");
    }

    if (userAgent.getDeleted() == 9) {
      throw new AuthenticationException("帐号异常");
    }
  }
}
