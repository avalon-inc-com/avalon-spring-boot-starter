package com.avaloninc.web.commons.api.response;

import com.avaloninc.web.commons.api.responses.PaginationResponse;
import com.avaloninc.web.commons.api.responses.Responses;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-05-03 18:17:35.
 * @Description:
 */
@RunWith(JUnit4.class)
public class PaginationResponseTest extends TestCase {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void test() throws JsonProcessingException {
    PaginationResponse<String> paginationResponse = Responses.successPaginationResponse(
        Lists.newArrayList("aaa"), 1, 5, 1);

    String json = OBJECT_MAPPER.writeValueAsString(paginationResponse);

    System.out.println(json);
  }

  @Test
  public void testWithPaginationResponse() throws IOException {
    String json = "{\"meta\":{\"host\":\"MacBookPro.local\",\"traceId\":\"4fbd57fb-e49d-40c1-8dfe-d84a7714e7e2\","
        + "\"code\":200,\"message\":\"\"},\"pagination\":{\"pageNum\":1,\"pageSize\":5,\"totalCount\":1},"
        + "\"data\":[\"aaa\"]}";
    TypeReference<PaginationResponse<String>> typeReference      = new TypeReference<PaginationResponse<String>>() { };
    PaginationResponse<String>                paginationResponse = OBJECT_MAPPER.readValue(json, typeReference);
    assertEquals(paginationResponse.getData(), Lists.newArrayList("aaa"));
    System.out.println("paginationResponse = " + paginationResponse);
  }
}