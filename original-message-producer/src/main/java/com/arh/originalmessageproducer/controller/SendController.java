package com.arh.originalmessageproducer.controller;

import com.arh.originalmessageproducer.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SendController {

    private RabbitMQConfig rabbitMQConfig;

    @RequestMapping("/test")
    public String test(){
      log.info(rabbitMQConfig.toString());
      return "hello";
    }
}
