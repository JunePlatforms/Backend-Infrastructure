package com.June.CourierNetwork.Controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/demo-controller")
@Hidden
public class DemoController {
  private static final Logger logger = LogManager.getLogger(DemoController.class);


  @GetMapping
  public ResponseEntity<String> sayHello() {
    logger.info("Hello from secured endpoint");

    return ResponseEntity.ok("Hello from secured endpoint");
  }

}
