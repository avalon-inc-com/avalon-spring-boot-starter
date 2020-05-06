package com.avaloninc.web.commons.api.response;

import com.avaloninc.web.commons.api.responses.RespMeta;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * @Author: wuzhiyu.
 * @Date: 2018-04-27 21:06:47.
 * @Description:
 */
@RunWith(JUnit4.class)
public class RespMetaTest extends TestCase {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  public void test() throws IOException {
    RespMeta respMeta = new RespMeta();
    String   json     = OBJECT_MAPPER.writeValueAsString(respMeta);
    System.out.println(json);
  }

  @Test
  public void testWithTraceIdAndHost() throws IOException {
    String json = "{\"host\":\"MacBookPro.local\",\"traceId\":\"3f0fd945-b100-4893-88a2-00974ce62667\",\"code\":200,"
        + "\"message\":\"\"}";

    RespMeta respMeta = OBJECT_MAPPER.readValue(json, RespMeta.class);
    assertEquals(respMeta.getHost(), "MacBookPro.local");
    assertEquals(respMeta.getTraceId(), "3f0fd945-b100-4893-88a2-00974ce62667");
  }

  @Test(expected = Exception.class)
  public void testWithoutHost() throws IOException {
    String json = "{\"host\":null,\"traceId\":\"3f0fd945-b100-4893-88a2-00974ce62667\",\"code\":200,"
        + "\"message\":\"\"}";

    OBJECT_MAPPER.readValue(json, RespMeta.class);
  }

  @Test(expected = Exception.class)
  public void testWithoutTraceId() throws IOException {
    String json = "{\"host\":\"MacBookPro.local\",\"traceId\":null,\"code\":200,"
        + "\"message\":\"\"}";

    OBJECT_MAPPER.readValue(json, RespMeta.class);
  }
}