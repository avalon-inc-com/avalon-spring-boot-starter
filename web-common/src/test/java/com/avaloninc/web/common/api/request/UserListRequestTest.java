package com.avaloninc.web.common.api.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 19:21:38.
 * @Description:
 */
@RunWith(JUnit4.class)
public class UserListRequestTest extends TestCase {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void testWithTraceId() throws IOException {
    UserListRequest userListRequest = new UserListRequest(UUID.randomUUID().toString());
    userListRequest.setName("John");
    String json = OBJECT_MAPPER.writeValueAsString(userListRequest);
    System.out.println(json);

    UserListRequest request = OBJECT_MAPPER.readValue(json, UserListRequest.class);
    assertEquals(request, userListRequest);
  }

  @Test
  public void testWithoutTraceId() throws IOException {
    UserListRequest userListRequest = new UserListRequest();
    userListRequest.setName("John");
    String json = OBJECT_MAPPER.writeValueAsString(userListRequest);
    System.out.println(json);

    UserListRequest request = OBJECT_MAPPER.readValue(json, UserListRequest.class);
    assertEquals(request.getName(), userListRequest.getName());
    assertEquals(request.getPageNum(), userListRequest.getPageNum());
    assertEquals(request.getPageSize(), userListRequest.getPageSize());
    assertNotNull(userListRequest.getTraceId());
    assertEquals(request.getTraceId(), userListRequest.getTraceId());
  }

  @Test
  public void testWithPage() throws IOException {
    UserListRequest userListRequest = new UserListRequest();
    userListRequest.setName("John");
    userListRequest.setPageNum(1);
    userListRequest.setPageSize(5);
    String json = OBJECT_MAPPER.writeValueAsString(userListRequest);
    System.out.println(json);

    UserListRequest request = OBJECT_MAPPER.readValue(json, UserListRequest.class);
    assertEquals(request.getPageNum(), 1);
    assertEquals(request.getPageSize(), 5);
  }
}