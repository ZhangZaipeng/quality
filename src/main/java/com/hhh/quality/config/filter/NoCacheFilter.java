package com.hhh.quality.config.filter;

import com.hhh.quality.platform.springmvc.WebContext;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    try {
      HttpServletResponse response = (HttpServletResponse) servletResponse;
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      response.setHeader("Pragma","No-cache");
      response.setHeader("Cache-Control","no-cache");
      response.setHeader("Expires", "0");

      filterChain.doFilter(request, response);
    } finally {
      WebContext.release();
    }
  }

  @Override
  public void destroy() {

  }
}
