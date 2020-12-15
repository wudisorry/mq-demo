package com.arh.messageconsumer.service;

import com.arh.message.entity.MessageInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class ReceiveMessageServiceImpl {

    public static final String TEST_QUEUE_NAME = "testQueue";

    public static final String TEST_DIRECT_EXCHANGE_NAME = "testDirectExchange";

    public static final String TEST_ROUTING_KEY_NAME = "testRoutingKey";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = TEST_QUEUE_NAME, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = TEST_DIRECT_EXCHANGE_NAME, type = ExchangeTypes.DIRECT),
            key = TEST_ROUTING_KEY_NAME
    ))
    @RabbitHandler
    public void receiveMessage(MessageInfo messageInfo, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        log.debug("收到消息，开始消费，当前线程：{}", Thread.currentThread().getName());
        log.debug("消息id：{}，消息内容：{}", messageInfo.getMessageId(), messageInfo.getInfo());
        /**
         * RabbitMQ推送消息给Consumer时，会附带一个Deliver Tag，相当于消息的唯一标识，
         * 以便Consumer在消息确认时告诉RabbitMQ到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        /**
         * 开始消费，处理业务
         */
        boolean flag = true;
        if (flag) {
            channel.basicAck(deliverTag, false);
        } else {
            channel.basicNack(deliverTag, false, true);
        }
    }
}
