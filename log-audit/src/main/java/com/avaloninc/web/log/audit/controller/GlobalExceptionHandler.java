package com.avaloninc.web.log.audit.controller;

import com.avaloninc.web.commons.api.responses.Response;
import com.avaloninc.web.commons.api.responses.Responses;
import com.avaloninc.web.commons.api.util.RequestUtils;
import com.avaloninc.web.log.audit.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wuzhiyu.
 * @Date: 2020-05-06 14:24:46.
 * @Description:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String FIELD_DELIMITER = "\n";

  private String getFieldErrorMessage(FieldError err) {
    return err.getField() + " " + err.getDefaultMessage() + "!";
  }

  /**
   * Handle response.
   *
   * @param ex the ex
   * @return the response
   */
  @ExceptionHandler(BindException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handle(final BindException ex) {
    String errorMsg = ex.getFieldErrorCount() > 0
        ? ex.getFieldErrors().stream()
            .map(this::getFieldErrorMessage).collect(Collectors.joining(FIELD_DELIMITER))
        : ex.getMessage();
    return Responses.errorResponse(HttpStatus.BAD_REQUEST.value(), errorMsg);
  }

  /**
   * Handle response.
   *
   * @param ex the ex
   * @return the response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handle(final MethodArgumentNotValidException ex) {
    String errorMsg = ex.getBindingResult().getFieldErrorCount() > 0
        ? ex.getBindingResult().getFieldErrors().stream()
            .map(this::getFieldErrorMessage).collect(Collectors.joining(FIELD_DELIMITER))
        : ex.getMessage();
    return Responses.errorResponse(HttpStatus.BAD_REQUEST.value(), errorMsg);
  }

  /**
   * Handle response.
   *
   * @param ex the ex
   * @return the response
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Response handle(final IllegalArgumentException ex) {
    return Responses.errorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Response handle(final UnauthorizedException ex) {
    return Responses.errorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
  }

  /**
   * Handle response.
   *
   * @param ex the ex
   * @return the response
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Response handle(HttpServletRequest request, Throwable ex) {
    Response response = Responses.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    String   url      = request.getRequestURL().toString();

    log.error("Internal Server Error with trace queryId {} at path {} using method {}.",
              RequestUtils.getTraceId(), url, request.getMethod(), ex);
    return response;
  }
}
