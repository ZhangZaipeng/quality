package com.hhh.quality.user.login;

import com.hhh.quality.platform.login.AuthenticationProvider;
import com.hhh.quality.platform.login.impl.CookieIdentityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SPIdentityValidator extends CookieIdentityValidator {

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Override
  public AuthenticationProvider getAuthenticationProvider() {
    return this.authenticationProvider;
  }

  @Override
  public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @Override
  protected String getPrincipalcookieName() {
    return "ES_PRINCIPAL";
  }

  @Override
  protected String getImmune() {
    return null;
  }

  @Override
  protected String getvisitorCookieName() {
    return "ES_VISITOR";
  }

  @Override
  protected String getBizLastLoginTimeCookieName() {
    return "ES_BIZ_LASTLOGIN";
  }

  @Override
  protected boolean singleClientLogin() {
    return false;
  }

  @Override
  protected String getLastLoginTimeCookieName() {
    return "ES_LASTLOGIN";
  }
}
