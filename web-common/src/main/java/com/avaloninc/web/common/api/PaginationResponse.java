package com.avaloninc.web.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-26 01:46:04.
 * @Description:
 */
@Data
public class PaginationResponse<T> {

  private final RespMeta   meta;
  private       List<T>    data;
  private final Pagination pagination;

  public PaginationResponse(@JsonProperty("meta") RespMeta meta,
                            @JsonProperty("pagination") Pagination pagination) {
    this.meta = meta;
    this.pagination = pagination;
  }
}
