package com.avaloninc.web.common.api;

import java.util.List;

/**
 * The type Responses.
 *
 * @Author: wuzhiyu.
 * @Date: 2018-04-26 01:47:13.
 * @Description:
 */
public class Responses {

  private static final Pagination DEFAULT_PAGINATION = new Pagination(1, 10, 0);

  private Responses() {
  }

  /**
   * Success response response.
   *
   * @param <T>  the type parameter
   * @param data the data
   * @return the response
   */
  public static <T> Response<T> successResponse(T data) {
    RespMeta    meta     = new RespMeta();
    Response<T> response = new Response<>();
    response.setData(data);
    response.setMeta(meta);
    return response;
  }

  /**
   * Success response response.
   *
   * @param <T>     the type parameter
   * @param data    the data
   * @param traceId the trace id
   * @return the response
   */
  public static <T> Response<T> successResponse(T data, String traceId) {
    RespMeta    meta     = new RespMeta(traceId);
    Response<T> response = new Response<>();
    response.setData(data);
    response.setMeta(meta);
    return response;
  }

  /**
   * Error response response.
   *
   * @param code    the code
   * @param message the message
   * @return the response
   */
  public static Response errorResponse(int code, String message) {
    RespMeta meta = new RespMeta();
    meta.setCode(code);
    meta.setMessage(message);

    Response response = new Response();
    response.setMeta(meta);
    return response;
  }

  /**
   * Error response response.
   *
   * @param code    the code
   * @param message the message
   * @param traceId the trace id
   * @return the response
   */
  public static Response errorResponse(int code, String message, String traceId) {
    RespMeta meta = new RespMeta(traceId);
    meta.setCode(code);
    meta.setMessage(message);

    Response response = new Response();
    response.setMeta(meta);
    return response;
  }

  /**
   * Success pagination response pagination response.
   *
   * @param <T>        the type parameter
   * @param data       the data
   * @param pageNum    the page num
   * @param pageSize   the page size
   * @param totalCount the total count
   * @return the pagination response
   */
  public static <T> PaginationResponse<T> successPaginationResponse(List<T> data, int pageNum, int pageSize,
                                                                    int totalCount) {
    RespMeta              meta       = new RespMeta();
    Pagination            pagination = new Pagination(pageNum, pageSize, totalCount);
    PaginationResponse<T> response   = new PaginationResponse<>(meta, pagination);
    response.setData(data);
    return response;
  }

  /**
   * Success pagination response pagination response.
   *
   * @param <T>        the type parameter
   * @param data       the data
   * @param pageNum    the page num
   * @param pageSize   the page size
   * @param totalCount the total count
   * @param traceId    the trace id
   * @return the pagination response
   */
  public static <T> PaginationResponse<T> successPaginationResponse(List<T> data, int pageNum, int pageSize,
                                                                    int totalCount, String traceId) {
    RespMeta   meta       = new RespMeta(traceId);
    Pagination pagination = new Pagination(pageNum, pageSize, totalCount);

    PaginationResponse<T> response = new PaginationResponse<>(meta, pagination);
    response.setData(data);
    return response;
  }

  /**
   * Error pagination response pagination response.
   *
   * @param code    the code
   * @param message the message
   * @return the pagination response
   */
  public static PaginationResponse errorPaginationResponse(int code, String message) {
    RespMeta meta = new RespMeta();
    meta.setCode(code);
    meta.setMessage(message);

    return new PaginationResponse(meta, DEFAULT_PAGINATION);
  }

  /**
   * Error pagination response pagination response.
   *
   * @param code    the code
   * @param message the message
   * @param traceId the trace id
   * @return the pagination response
   */
  public static PaginationResponse errorPaginationResponse(int code, String message, String traceId) {
    RespMeta meta = new RespMeta(traceId);
    meta.setCode(code);
    meta.setMessage(message);

    return new PaginationResponse(meta, DEFAULT_PAGINATION);
  }
}
