package com.avaloninc.web.demo.request;

import com.avaloninc.web.commons.api.requests.base.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-24 00:24:32.
 * @Description:
 */
@Data
public class TestRequest extends BaseRequest {
  @NotBlank
  private String content;
}
