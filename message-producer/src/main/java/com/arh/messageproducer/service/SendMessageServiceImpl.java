package com.arh.messageproducer.service;

import com.arh.message.entity.MessageInfo;
import com.arh.messageproducer.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements ISendMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(MessageInfo messageInfo) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TEST_DIRECT_EXCHANGE_NAME, RabbitMQConfig.TEST_ROUTING_KEY_NAME, messageInfo, new CorrelationData(messageInfo.getMessageId()));
    }
}
