package com.avaloninc.web.log.audit.exception;

/**
 * The type Unauthorized exception.
 *
 * @Author: wuzhiyu.
 * @Date: 2020 -07-08 12:24:06.
 * @Description:
 */
public class UnauthorizedException extends RuntimeException {
  /**
   * Instantiates a new Unauthorized exception.
   */
  public UnauthorizedException() {
    super();
  }

  /**
   * Instantiates a new Unauthorized exception.
   *
   * @param message the message
   */
  public UnauthorizedException(String message) {
    super(message);
  }
}
