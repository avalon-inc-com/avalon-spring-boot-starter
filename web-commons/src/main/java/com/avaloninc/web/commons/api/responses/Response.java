package com.avaloninc.web.commons.api.responses;

import lombok.Data;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-26 01:41:20.
 * @Description:
 */
@Data
public class Response<T> {
  private RespMeta meta;
  private T        data;
}
