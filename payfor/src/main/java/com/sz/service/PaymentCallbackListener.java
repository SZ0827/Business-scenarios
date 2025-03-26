package com.sz.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "payment_callback")
public class PaymentCallbackListener {
    private final OrderService orderService;

    public PaymentCallbackListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void process(Long orderId){
        orderService.updataOrderStatus(orderId,"PAYMENT_SUCCESS");
    }
}
