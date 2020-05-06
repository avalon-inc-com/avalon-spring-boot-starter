package com.avaloninc.web.commons.api.response;

import com.google.common.collect.ImmutableList;

import com.avaloninc.web.commons.api.responses.PaginationResponse;
import com.avaloninc.web.commons.api.responses.Response;
import com.avaloninc.web.commons.api.responses.Responses;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-08-11 23:15:10.
 * @Description:
 */
@RunWith(JUnit4.class)
public class ResponsesTest extends TestCase {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String       TRACE_ID      = "9c2031cd-0451-4a8e-9f23-0da0be42d66d";

  @Test
  public void successResponse() throws IOException {
    String json = OBJECT_MAPPER.writeValueAsString(Responses.successResponse("aaa"));
    System.out.println(json);

    TypeReference<Response<String>> typeReference = new TypeReference<Response<String>>() {
    };
    Response<String> response = OBJECT_MAPPER.readValue(json, typeReference);
    assertEquals(response.getData(), "aaa");
  }

  @Test
  public void successResponseWithTraceId() throws IOException {
    String jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.successResponse("aaa", TRACE_ID));
    System.out.println(jsonStr);

    TypeReference<Response<String>> type = new TypeReference<Response<String>>() {
    };
    Response<String> response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getData(), "aaa");
    assertEquals(response.getMeta().getTraceId(), TRACE_ID);
  }

  @Test
  public void errorResponse() throws IOException {
    String message = "Server Error.";
    String jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.errorResponse(500, message));
    System.out.println(jsonStr);

    TypeReference<Response> type = new TypeReference<Response>() {
    };
    Response response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getMeta().getMessage(), message);
    assertEquals(response.getMeta().getCode(), 500);
  }

  @Test
  public void errorResponseWithTraceId() throws IOException {
    String message = "Server Error.";
    String jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.errorResponse(500, message, TRACE_ID));
    System.out.println(jsonStr);

    TypeReference<Response> type = new TypeReference<Response>() {
    };
    Response response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getMeta().getMessage(), message);
    assertEquals(response.getMeta().getCode(), 500);
    assertEquals(response.getMeta().getTraceId(), TRACE_ID);
  }

  @Test
  public void successPaginationResponse() throws IOException {
    List<String> param   = ImmutableList.of("aaa");
    String       jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.successPaginationResponse(param, 1, 10, 1));
    System.out.println(jsonStr);

    TypeReference<PaginationResponse<String>> type = new TypeReference<PaginationResponse<String>>() {
    };
    PaginationResponse<String> response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getData(), param);
    assertEquals(response.getPagination().getPageNum(), 1);
    assertEquals(response.getPagination().getPageSize(), 10);
    assertEquals(response.getPagination().getTotalCount(), 1);
  }

  @Test
  public void successPaginationResponseWithTraceId() throws IOException {
    List<String> param   = ImmutableList.of("aaa");
    String       jsonStr = OBJECT_MAPPER.writeValueAsString(
        Responses.successPaginationResponse(param, 1, 10, 1, TRACE_ID));
    System.out.println(jsonStr);

    TypeReference<PaginationResponse<String>> type = new TypeReference<PaginationResponse<String>>() {
    };
    PaginationResponse<String> response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getMeta().getTraceId(), TRACE_ID);
    assertEquals(response.getData(), param);
    assertEquals(response.getPagination().getPageNum(), 1);
    assertEquals(response.getPagination().getPageSize(), 10);
    assertEquals(response.getPagination().getTotalCount(), 1);
  }

  @Test
  public void errorPaginationResponse() throws IOException {
    String message = "Server Error.";
    String jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.errorPaginationResponse(500, message));
    System.out.println(jsonStr);

    TypeReference<PaginationResponse<String>> type = new TypeReference<PaginationResponse<String>>() {
    };
    PaginationResponse<String> response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getMeta().getMessage(), message);
    assertEquals(response.getMeta().getCode(), 500);
  }

  @Test
  public void errorPaginationResponseWithTraceId() throws IOException {
    String message = "Server Error.";
    String jsonStr = OBJECT_MAPPER.writeValueAsString(Responses.errorPaginationResponse(500, message, TRACE_ID));
    System.out.println(jsonStr);

    TypeReference<PaginationResponse<String>> type = new TypeReference<PaginationResponse<String>>() {
    };
    PaginationResponse<String> response = OBJECT_MAPPER.readValue(jsonStr, type);

    assertEquals(response.getMeta().getMessage(), message);
    assertEquals(response.getMeta().getCode(), 500);
    assertEquals(response.getMeta().getTraceId(), TRACE_ID);
  }
}