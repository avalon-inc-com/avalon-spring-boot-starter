package com.avaloninc.web.commons.api.base;

import com.google.common.base.Strings;

import com.avaloninc.web.commons.api.util.RequestUtils;
import lombok.Data;

import java.util.UUID;

/**
 * The type Base request.
 *
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 01:23:50.
 * @Description:
 */
@Data
public abstract class BaseRequest {
  private String traceId;

  /**
   * Instantiates a new Base request.
   *
   * @param traceId the trace id
   */
  public BaseRequest(String traceId) {
    UUID.fromString(traceId);
    this.traceId = traceId;
  }

  /**
   * Instantiates a new Base request.
   */
  public BaseRequest() {
    String traceId = RequestUtils.getTraceId();
    this.traceId = Strings.isNullOrEmpty(traceId) ? UUID.randomUUID().toString() : traceId;
  }
}
