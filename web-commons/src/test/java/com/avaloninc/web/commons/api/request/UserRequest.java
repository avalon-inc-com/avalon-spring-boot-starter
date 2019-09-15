package com.avaloninc.web.commons.api.request;

import com.avaloninc.web.commons.api.base.BaseRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 01:49:38.
 * @Description:
 */
@Data
@NoArgsConstructor
public class UserRequest extends BaseRequest {
  private String name;

  public UserRequest(String traceId) {
    super(traceId);
  }
}
