package com.avaloninc.web.log.audit.exception;

/**
 * The type Not found exception.
 *
 * @Author: wuzhiyu.
 * @Date: 2020-07-21 15:29:45.
 * @Description:
 */
public class NotFoundException extends RuntimeException {
  /**
   * Instantiates a new Not found exception.
   */
  public NotFoundException() {
    super();
  }

  /**
   * Instantiates a new Not found exception.
   *
   * @param message the message
   */
  public NotFoundException(String message) {
    super(message);
  }
}
