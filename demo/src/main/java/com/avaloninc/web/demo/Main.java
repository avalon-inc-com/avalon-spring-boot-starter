package com.avaloninc.web.demo;


import com.avaloninc.web.common.api.Response;
import com.avaloninc.web.common.api.Responses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wuzhiyu.
 * @Date: 2019-02-23 17:32:05.
 * @Description:
 */
@SpringBootApplication
@RestController
@RequestMapping("api")
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class);
  }

  @GetMapping
  public Response<Boolean> get() {
    return Responses.successResponse(true);
  }

  @PostMapping
  public Response<TestRequest> post(@RequestBody TestRequest request) {
    return Responses.successResponse(request);
  }

  @GetMapping("white_list/get")
  public Response<Boolean> testWhiteList() {
    return Responses.successResponse(true);
  }

  @PostMapping("white_list/post")
  public Response<TestRequest> testWhiteList(@RequestBody TestRequest request) {
    return Responses.successResponse(request);
  }
}
