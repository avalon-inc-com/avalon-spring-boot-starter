package com.avaloninc.web.demo;

import com.avaloninc.web.commons.api.base.BaseRequest;
import lombok.Data;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-24 00:24:32.
 * @Description:
 */
@Data
public class TestRequest extends BaseRequest {
  private String content;
}
