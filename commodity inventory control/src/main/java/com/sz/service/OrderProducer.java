package com.sz.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "order_queue";

    public void sendOrderMessage(Long productId) {
        //将信息发送到指定队列
        rabbitTemplate.convertAndSend(QUEUE_NAME, productId);
    }
}
