package com.arh.messageproducer.controller;

import com.arh.message.entity.MessageInfo;
import com.arh.messageproducer.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SendMessageController {

    @Autowired
    private ISendMessageService sendMessageService;

    @RequestMapping("/testSend")
    public String testSend() {

        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setId(1);
        messageInfo.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        messageInfo.setName("tome");
        messageInfo.setInfo("hello");
        sendMessageService.send(messageInfo);
        return "success";
    }
}
