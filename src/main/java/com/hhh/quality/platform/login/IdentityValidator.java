package com.hhh.quality.platform.login;

/**
 * 身份验证接口
 *
 * @author jianguo.xu
 * @version 1.0, 2011-2-17
 */
public interface IdentityValidator {

  /**
   * 判断是否是访问者
   *
   * @return true 表示是访问者 false 表示不是访问者
   * @author jianguo.xu
   */
  public boolean isVisited();

  /**
   * 得到当前访问者用户名
   *
   * @return 当前访问者用户名 没有用户返回null
   * @author jianguo.xu
   */
  public String currentVisitor();

  /**
   * 判断用户是否登录
   *
   * @return true 表示是已登录 false 表示没有登录
   * @author jianguo.xu
   */
  public boolean isLogined();

  /**
   * 得到当前用户
   *
   * @return 当前用户 没有用户返回null
   * @author jianguo.xu
   */
  public Principal currentPrincipal();

  /**
   * 得到当前用户 根据 request
   * @return 当前用户 没有用户返回null
   */
  public Principal currentPrincipal(String token);

  /**
   * 验证登录是否成功
   *
   * @return 返回登录后的认证主体
   * @author jianguo.xu
   */
  public Principal login(Verifier verifier) throws AuthenticationException;

  public Principal login_auto(Verifier verifier) throws AuthenticationException;

  /**
   * 验证登录是否成功
   *
   * @return 返回登录后的认证主体
   * @author jianguo.xu
   */
  public Principal login(Verifier verifier, String ip) throws AuthenticationException;

  /**
   * 退出登录状态
   *
   * @author jianguo.xu
   */
  public void logout();

  /**
   * 得到验证提供者，用于获得用户信息和登录验证
   *
   * @return 验证提供者 *@see com.xhbs.validate.AuthenticationProvider
   * @author jianguo.xu
   */
  public AuthenticationProvider getAuthenticationProvider();

  /**
   * 设置验证提供者，用于获得用户信息和登录验证
   *
   * @author jianguo.xu
   */
  public void setAuthenticationProvider(AuthenticationProvider authenticationProvider);
}
