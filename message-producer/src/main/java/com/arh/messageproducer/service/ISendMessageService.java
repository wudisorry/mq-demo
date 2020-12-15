package com.arh.messageproducer.service;

import com.arh.message.entity.MessageInfo;

public interface ISendMessageService {

    void send(MessageInfo message);
}
