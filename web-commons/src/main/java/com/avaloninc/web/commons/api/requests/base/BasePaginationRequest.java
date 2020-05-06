package com.avaloninc.web.commons.api.requests.base;

import com.google.common.base.Strings;

import com.avaloninc.web.commons.api.util.RequestUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * The type Base pagination request.
 *
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 01:27:31.
 * @Description:
 */
@Data
public abstract class BasePaginationRequest {
  private String traceId;
  private int    pageNum;
  private int    pageSize;

  /**
   * Instantiates a new Base pagination request.
   *
   * @param traceId the trace id
   */
  public BasePaginationRequest(@JsonProperty("traceId") String traceId) {
    UUID.fromString(traceId);
    this.traceId = traceId;
    this.pageNum = 0;
    this.pageSize = 10;
  }

  /**
   * Instantiates a new Base pagination request.
   */
  public BasePaginationRequest() {
    String traceId = RequestUtils.getTraceId();
    this.traceId = Strings.isNullOrEmpty(traceId) ? UUID.randomUUID().toString() : traceId;
    this.pageNum = 0;
    this.pageSize = 10;
  }

  /**
   * Sets page num.
   *
   * @param pageNum the page num
   */
  public void setPageNum(int pageNum) {
    assert pageNum >= 0;
    this.pageNum = pageNum;
  }

  /**
   * Sets page size.
   *
   * @param pageSize the page size
   */
  public void setPageSize(int pageSize) {
    assert pageSize > 0;
    this.pageSize = pageSize;
  }
}
