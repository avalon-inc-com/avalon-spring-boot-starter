package com.avaloninc.web.common.api;

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
