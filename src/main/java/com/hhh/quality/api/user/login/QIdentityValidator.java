package com.hhh.quality.api.user.login;

import com.hhh.quality.platform.login.AuthenticationProvider;
import com.hhh.quality.platform.login.impl.CookieIdentityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QIdentityValidator extends CookieIdentityValidator {

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
    return "Q_PRINCIPAL";
  }

  @Override
  protected String getImmune() {
    return null;
  }

  @Override
  protected String getvisitorCookieName() {
    return "Q_VISITOR";
  }

  @Override
  protected String getBizLastLoginTimeCookieName() {
    return "Q_BIZ_LASTLOGIN";
  }

  @Override
  protected boolean singleClientLogin() {
    return false;
  }

  @Override
  protected String getLastLoginTimeCookieName() {
    return "Q_LASTLOGIN";
  }
}
