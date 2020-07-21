package com.avaloninc.web.log.audit.exception;

/**
 * The type Conflict exception.
 *
 * @Author: wuzhiyu.
 * @Date: 2020-07-21 15:32:00.
 * @Description:
 */
public class ConflictException extends RuntimeException {
  /**
   * Instantiates a new Conflict exception.
   */
  public ConflictException() {
    super();
  }

  /**
   * Instantiates a new Conflict exception.
   *
   * @param message the message
   */
  public ConflictException(String message) {
    super(message);
  }
}
