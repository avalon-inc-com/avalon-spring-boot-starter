package com.avaloninc.web.log.audit.interceptor;

import com.avaloninc.web.commons.api.util.RequestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 14:46:36.
 * @Description:
 */
@Slf4j
public class RequestAuditInterceptor implements HandlerInterceptor {

  private static final ObjectMapper OBJECT_MAPPER   = new ObjectMapper();
  private static final String       TRACE_ID_NAME   = "traceId";
  private static final String       DEFAULT_USER    = "default_user";
  private static final String       AND_SEPARATOR   = "&";
  private static final String       EQUAL_SEPARATOR = "=";

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
      Map<String, String> parameterMap = this.getQueryParameterMap(request);
      Map<String, Object> payloadMap   = this.getRequestPayload(request);

      String traceId = this.getOrGenerateTraceId(method, parameterMap, payloadMap);

      String payloadString  = this.getMapToJson(payloadMap);
      String queryMapString = this.getMapToJson(parameterMap);

      String requestLog = String.join(
          this.logPartSeparator,
          Lists.newArrayList(method, requestURI, queryMapString, payloadString));

      RequestUtils.setTraceId(traceId);
      RequestUtils.setUserName(DEFAULT_USER);
      RequestUtils.setStartTime(System.currentTimeMillis());
      RequestUtils.setRequestParts(requestLog);
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
      String requestParts = RequestUtils.getRequestParts();
      Long   startTime    = RequestUtils.getStartTime();

      final String dur = System.currentTimeMillis() - startTime + " ms";
      final List<String> logParts = Lists.newArrayList(RequestUtils.getTraceId(),
                                                       RequestUtils.getUserName(),
                                                       requestParts, dur);
      log.info(String.join(this.logPartSeparator, logParts));

      RequestUtils.removeThreadLocalVars();
    }
  }


  private Map<String, String> getQueryParameterMap(HttpServletRequest request) {
    String queryString = request.getQueryString();
    if (Strings.isNullOrEmpty(queryString)) {
      return Collections.emptyMap();
    } else {
      Map<String, String> parameters = Maps.newHashMap();
      Splitter.on(AND_SEPARATOR).split(queryString).forEach((pair) -> {
        String[] split = pair.split(EQUAL_SEPARATOR);
        parameters.put(split[0], split.length > 1 ? split[1] : "");
      });
      return parameters;
    }
  }

  private Map<String, Object> getRequestPayload(HttpServletRequest request) {
    try {
      ServletInputStream inputStream = request.getInputStream();
      if (Objects.isNull(inputStream) || request.getContentLengthLong() <= 0) {
        return Collections.emptyMap();
      } else {
        return OBJECT_MAPPER.readValue(inputStream, Map.class);
      }
    } catch (IOException ex) {
      return Collections.emptyMap();
    }
  }
}
