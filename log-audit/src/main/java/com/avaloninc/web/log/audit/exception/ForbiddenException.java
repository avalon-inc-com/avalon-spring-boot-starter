package com.avaloninc.web.log.audit.exception;

/**
 * The type Forbidden exception.
 *
 * @Author: wuzhiyu.
 * @Date: 2020-07-21 15:28:19.
 * @Description:
 */
public class ForbiddenException extends RuntimeException {
  /**
   * Instantiates a new Forbidden exception.
   */
  public ForbiddenException() {
    super();
  }

  /**
   * Instantiates a new Forbidden exception.
   *
   * @param message the message
   */
  public ForbiddenException(String message) {
    super(message);
  }
}
