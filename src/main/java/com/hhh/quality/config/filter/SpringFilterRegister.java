package com.hhh.quality.config.filter;

import com.hhh.quality.platform.springmvc.WebContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFilterRegister {

  @Bean
  public FilterRegistrationBean WebContextFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new WebContextFilter());
    registration.addUrlPatterns("/*");
    registration.setName("webContextFilter");
    registration.setOrder(1);
    return registration;
  }

  @Bean
  public FilterRegistrationBean NoCacheFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new NoCacheFilter());

    registration.addUrlPatterns("/openapi/*");
    registration.addUrlPatterns("/wap/*");
    registration.addUrlPatterns("/app/*");
    registration.addUrlPatterns("/web/*");

    registration.setName("noCacheFilter");
    registration.setOrder(10);
    return registration;
  }

}
