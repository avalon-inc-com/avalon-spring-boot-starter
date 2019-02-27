package com.avaloninc.web.log.audit.wrapper;

import com.google.common.collect.Lists;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-22 15:26:03.
 * @Description:
 */
public class ContentCachingHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private static final List<MediaType> CONTENT_TYPES_TO_CACHE = Lists.newArrayList(
      MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8,
      MediaType.APPLICATION_FORM_URLENCODED);

  private final boolean needToCache;

  private byte[] cachedContent;

  @SneakyThrows(IOException.class)
  public ContentCachingHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
    HttpMethod httpMethod  = HttpMethod.valueOf(request.getMethod());
    boolean    matchMethod = httpMethod != HttpMethod.GET;

    String contentType = request.getContentType();
    boolean matchMediaType = CONTENT_TYPES_TO_CACHE
        .stream()
        .anyMatch(item -> Objects.nonNull(contentType)
                          && item.includes(MediaType.parseMediaType(contentType)));

    this.needToCache = matchMethod && matchMediaType;
    if (needToCache) {
      this.cachedContent = IOUtils.toByteArray(request.getInputStream());
    }
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return this.needToCache ? new ContentCachingInputStream(cachedContent) : super.getInputStream();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    if (this.needToCache) {
      return new BufferedReader(
          new InputStreamReader(this.getInputStream(), getCharacterEncoding()));
    } else {
      return super.getReader();
    }
  }


  private class ContentCachingInputStream extends ServletInputStream {

    private final ByteArrayInputStream inputStream;

    ContentCachingInputStream(byte[] content) {
      this.inputStream = new ByteArrayInputStream(content);
    }

    @Override
    public int read() throws IOException {
      return this.inputStream.read();
    }

    @Override
    public boolean isFinished() {
      throw new NotImplementedException();
    }

    @Override
    public boolean isReady() {
      throw new NotImplementedException();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      throw new NotImplementedException();
    }
  }
}
