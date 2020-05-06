package com.avaloninc.web.log.audit.config;

import com.avaloninc.web.log.audit.controller.GlobalExceptionHandler;
import com.avaloninc.web.log.audit.filter.RequestBodyWrapperFilter;
import com.avaloninc.web.log.audit.interceptor.RequestAuditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 15:15:18.
 * @Description:
 */
@Configuration
@ConditionalOnProperty(value = "log.request.audit.enable", havingValue = "true")
public class RequestAuditConfiguration extends WebMvcConfigurerAdapter {

  private final String   logPartSeparator;
  private final String[] uriWhiteList;

  @Autowired
  public RequestAuditConfiguration(@Value("${log.request.audit.separator:'||'}") String logPartSeparator,
                                   @Value("${log.request.audit.whitelist:''}") String[] uriWhiteList) {
    this.logPartSeparator = logPartSeparator;
    this.uriWhiteList = uriWhiteList;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new RequestAuditInterceptor(logPartSeparator, uriWhiteList));
  }

  @Bean
  public RequestBodyWrapperFilter getRequestBodyWrapperFilter() {
    return new RequestBodyWrapperFilter();
  }

  @Bean
  public GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }
}
