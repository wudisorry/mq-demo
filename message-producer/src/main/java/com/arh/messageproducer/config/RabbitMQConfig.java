package com.arh.messageproducer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    public static final String TEST_QUEUE_NAME = "testQueue";

    public static final String TEST_DIRECT_EXCHANGE_NAME = "testDirectExchange";

    public static final String TEST_ROUTING_KEY_NAME = "testRoutingKey";

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        //消息是否发到exchange上的回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String messageId = correlationData != null ? correlationData.getId() : "空";
            if (ack) {
                log.debug("消息发送成功，id：{}", messageId);
            } else {
                log.info("消息发送失败，id：{}，原因：{}", messageId, cause);
            }
        });
        //消息从exchange发到queue上失败的回调函数,mandatory属性一定要设置为true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("消息从Exchange发到Queue失败");
        });

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue testQueue() {
        //durable属性表示队列是否支持持久化
        return new Queue(TEST_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange testDirectExchange() {
        return new DirectExchange(TEST_DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(testQueue()).to(testDirectExchange()).with(TEST_ROUTING_KEY_NAME);
    }

}
