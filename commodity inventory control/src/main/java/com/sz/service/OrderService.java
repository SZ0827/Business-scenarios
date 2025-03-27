package com.sz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final RabbitTemplate rabbitTemplate;
    public void confirmOrder(String orderId){
        //发送订单确定消息到RabbitMQ的交换机，再由交换机路由匹配队列
        rabbitTemplate.convertAndSend("order-exchange","order.confirm",orderId);
    }
}
