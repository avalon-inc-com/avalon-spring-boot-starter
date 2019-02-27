package com.avaloninc.web.common.api;

import com.google.common.base.Strings;

import com.avaloninc.web.common.api.util.RequestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-26 01:42:23.
 * @Description:
 */
@Data
public class RespMeta {
  private static String HOST;

  static {
    try {
      HOST = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      HOST = UUID.randomUUID().toString();
    }
  }

  private       int    code;
  private       String message;
  private final String host;
  private final String traceId;

  public RespMeta(@JsonProperty("host") String host, @JsonProperty("traceId") String traceId) {
    UUID.fromString(traceId);
    Objects.requireNonNull(host, "Host should not be null.");
    this.traceId = traceId;
    this.host = host;
  }

  public RespMeta(String traceId) {
    this.traceId = traceId;
    this.host = HOST;
    this.message = "success";
    this.code = 200;
  }

  public RespMeta() {
    String traceId = RequestUtils.getTraceId();
    this.traceId = Strings.isNullOrEmpty(traceId) ? UUID.randomUUID().toString() : traceId;
    this.host = HOST;
    this.message = "success";
    this.code = 200;
  }
}
