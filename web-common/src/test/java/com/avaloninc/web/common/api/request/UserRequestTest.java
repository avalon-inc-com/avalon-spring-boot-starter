package com.avaloninc.web.common.api.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 01:31:02.
 * @Description:
 */
@RunWith(JUnit4.class)
public class UserRequestTest extends TestCase {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String       TRACE_ID      = "87bde138-8068-44ed-9a4a-abd9b1d1dc77";

  @Test
  public void test() throws IOException {
    UserRequest userRequest = new UserRequest();
    userRequest.setName("John");
    String jsonStr = OBJECT_MAPPER.writeValueAsString(userRequest);
    System.out.println(jsonStr);

    UserRequest request = OBJECT_MAPPER.readValue(jsonStr, UserRequest.class);
    assertEquals(userRequest.getName(), request.getName());
    assertNotNull(request.getTraceId());
  }

  @Test
  public void testRequestWithTraceId() throws IOException {
    UserRequest userRequest = new UserRequest(TRACE_ID);
    String json = OBJECT_MAPPER.writeValueAsString(userRequest);
    System.out.println(json);

    UserRequest request = OBJECT_MAPPER.readValue(json, UserRequest.class);
    assertEquals(userRequest, request);
  }
}