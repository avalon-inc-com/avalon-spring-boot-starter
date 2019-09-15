package com.avaloninc.web.commons.api.request;

import com.avaloninc.web.commons.api.base.BasePaginationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 19:11:53.
 * @Description:
 */
@Data
@NoArgsConstructor
public class UserListRequest extends BasePaginationRequest {
  private String name;

  public UserListRequest(String traceId) {
    super(traceId);
  }
}
