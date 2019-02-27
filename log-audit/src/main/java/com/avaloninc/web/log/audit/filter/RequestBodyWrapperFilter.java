package com.avaloninc.web.log.audit.filter;

import com.avaloninc.web.log.audit.wrapper.ContentCachingHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 15:03:05.
 * @Description:
 */
@Slf4j
public class RequestBodyWrapperFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.debug("Filter init " + filterConfig.getFilterName());
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    log.debug("Before filter " + RequestBodyWrapperFilter.class.getCanonicalName());

    if (servletRequest instanceof HttpServletRequest) {
      filterChain.doFilter(new ContentCachingHttpServletRequestWrapper(
          (javax.servlet.http.HttpServletRequest) servletRequest), servletResponse);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
    log.debug("After filter " + RequestBodyWrapperFilter.class.getCanonicalName());
  }

  @Override
  public void destroy() {
    log.debug("Filter destroy " + RequestBodyWrapperFilter.class.getCanonicalName());
  }
}
