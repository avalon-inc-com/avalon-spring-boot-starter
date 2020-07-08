package com.avaloninc.web.commons.api.util;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 17:10:07.
 * @Description:
 */
public class RequestUtils {

  private static final ThreadLocal<Boolean> LOG_FLAG      = new ThreadLocal<>();
  private static final ThreadLocal<Long>    START_TIME    = new ThreadLocal<>();
  private static final ThreadLocal<String>  REQUEST_PARTS = new ThreadLocal<>();
  private static final ThreadLocal<String>  TRACE_ID      = new ThreadLocal<>();
  private static final ThreadLocal<String>  USER_NAME     = new ThreadLocal<>();

  public static Boolean getLogFlag() {
    return LOG_FLAG.get();
  }

  public static Long getStartTime() {
    return START_TIME.get();
  }

  public static String getRequestParts() {
    return REQUEST_PARTS.get();
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

  public static void setRequestParts(String requestParts) {
    REQUEST_PARTS.set(requestParts);
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
    REQUEST_PARTS.remove();
    TRACE_ID.remove();
    USER_NAME.remove();
  }
}
