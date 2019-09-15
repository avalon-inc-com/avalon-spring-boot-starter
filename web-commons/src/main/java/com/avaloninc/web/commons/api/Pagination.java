package com.avaloninc.web.commons.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-26 01:44:46.
 * @Description:
 */
@Data
public class Pagination {
  private final int pageNum;
  private final int pageSize;
  private final int totalCount;

  @JsonCreator
  public Pagination(@JsonProperty("pageNum") int pageNum,
                    @JsonProperty("pageSize") int pageSize,
                    @JsonProperty("totalCount") int totalCount) {
    assert pageNum >= 0;
    assert pageSize > 0;
    assert totalCount >= 0;
    this.pageNum = pageNum;
    this.pageSize = pageSize;
    this.totalCount = totalCount;
  }
}
