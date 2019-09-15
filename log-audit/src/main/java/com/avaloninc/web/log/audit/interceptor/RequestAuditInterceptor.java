package com.avaloninc.web.log.audit.interceptor;

import com.google.common.collect.Lists;

import com.avaloninc.web.commons.api.util.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 14:46:36.
 * @Description:
 */
@Slf4j
public class RequestAuditInterceptor implements HandlerInterceptor {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String       TRACE_ID_NAME = "traceId";
  private static final String       DEFAULT_USER  = "default_user";

  private final String   logPartSeparator;
  private final String[] uriAuditWhiteList;

  public RequestAuditInterceptor(String logPartSeparator, String[] uriAuditWhiteList) {
    this.logPartSeparator = logPartSeparator;
    this.uriAuditWhiteList = uriAuditWhiteList;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
    log.debug("Pre interceptor handle.");

    String  requestURI       = request.getRequestURI();
    boolean isUriInWhiteList = Stream.of(this.uriAuditWhiteList).anyMatch(requestURI::matches);
    RequestUtils.setLogFlag(!isUriInWhiteList);
    if (RequestUtils.getLogFlag()) {

      String              method       = request.getMethod();
      Map<String, String> parameterMap = RequestUtils.getQueryParameterMap(request);
      Map<String, Object> payloadMap   = RequestUtils.getRequestPayload(request);

      String traceId = this.getOrGenerateTraceId(method, parameterMap, payloadMap);

      String payloadString  = this.getMapToJson(payloadMap);
      String queryMapString = this.getMapToJson(parameterMap);

      String userName = DEFAULT_USER;

      String requestLog = String.join(
          this.logPartSeparator,
          Lists.newArrayList(traceId, userName, method, requestURI, queryMapString, payloadString));

      RequestUtils.setTraceId(traceId);
      RequestUtils.setUserName(userName);
      RequestUtils.setStartTime(System.currentTimeMillis());
      RequestUtils.setLogParts(requestLog);
    } else {
      RequestUtils.setTraceId(UUID.randomUUID().toString());
    }
    return true;
  }

  private String getMapToJson(Map map) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(map);
  }

  private String getOrGenerateTraceId(String method, Map<String, String> parameterMap,
                                      Map<String, Object> payloadMap) {
    if (RequestMethod.valueOf(method) == RequestMethod.GET) {
      return parameterMap.getOrDefault(TRACE_ID_NAME, UUID.randomUUID().toString());
    } else {
      return (String) payloadMap.getOrDefault(TRACE_ID_NAME, UUID.randomUUID().toString());
    }
  }


  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
                         Object o, ModelAndView modelAndView) throws Exception {
    log.debug("Post interceptor handle.");
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                              Object o, Exception e) throws Exception {
    log.debug("After interceptor completion.");

    if (RequestUtils.getLogFlag()) {
      String logParts  = RequestUtils.getLogParts();
      Long   startTime = RequestUtils.getStartTime();

      long dur = System.currentTimeMillis() - startTime;
      log.info(logParts + this.logPartSeparator + dur + " ms");

      RequestUtils.removeThreadLocalVars();
    }
  }
}
