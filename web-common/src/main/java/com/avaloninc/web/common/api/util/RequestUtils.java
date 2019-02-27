package com.avaloninc.web.common.api.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 17:10:07.
 * @Description:
 */
public class RequestUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final ThreadLocal<Boolean> LOG_FLAG   = new ThreadLocal<>();
  private static final ThreadLocal<Long>    START_TIME = new ThreadLocal<>();
  private static final ThreadLocal<String>  LOG_PARTS  = new ThreadLocal<>();
  private static final ThreadLocal<String>  TRACE_ID   = new ThreadLocal<>();
  private static final ThreadLocal<String>  USER_NAME  = new ThreadLocal<>();

  public static Boolean getLogFlag() {
    return LOG_FLAG.get();
  }

  public static Long getStartTime() {
    return START_TIME.get();
  }

  public static String getLogParts() {
    return LOG_PARTS.get();
  }

  public static String getTraceId() {
    return TRACE_ID.get();
  }

  public static String getUserName() {
    return USER_NAME.get();
  }

  public static void setLogFlag(boolean logFlag) {
    LOG_FLAG.set(logFlag);
  }

  public static void setStartTime(long startTime) {
    START_TIME.set(startTime);
  }

  public static void setLogParts(String logParts) {
    LOG_PARTS.set(logParts);
  }

  public static void setTraceId(String traceId) {
    TRACE_ID.set(traceId);
  }

  public static void setUserName(String userName) {
    USER_NAME.set(userName);
  }

  public static void removeThreadLocalVars() {
    LOG_FLAG.remove();
    START_TIME.remove();
    LOG_PARTS.remove();
    TRACE_ID.remove();
    USER_NAME.remove();
  }

  public static Map<String, String> getQueryParameterMap(HttpServletRequest request) {
    String queryString = request.getQueryString();
    if (Strings.isNullOrEmpty(queryString)) {
      return Collections.emptyMap();
    } else {
      Map<String, String> parameters = Maps.newHashMap();
      Splitter.on("&").split(queryString).forEach((pair) -> {
        String[] split = pair.split("=");
        parameters.put(split[0], split.length > 1 ? split[1] : "");
      });
      return parameters;
    }
  }

  @SneakyThrows(IOException.class)
  public static Map<String, Object> getRequestPayload(HttpServletRequest request) {
    ServletInputStream inputStream = request.getInputStream();
    if (Objects.isNull(inputStream) || request.getContentLengthLong() <= 0) {
      return Collections.emptyMap();
    } else {
      return OBJECT_MAPPER.readValue(inputStream, Map.class);
    }
  }
}
