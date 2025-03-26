package com.sz.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "order_timeout")
public class OrderTimeoutListener {
    private final OrderService orderService;
    private final RedisTemplate redisTemplate;

    public OrderTimeoutListener(OrderService orderService, RedisTemplate redisTemplate) {
        this.orderService = orderService;
        this.redisTemplate = redisTemplate;
    }
    @RabbitHandler
    public void process(Long orderId){
        String status= (String) redisTemplate.opsForValue().get("order_"+orderId);
        if("PENDING".equals(status)){
            orderService.updataOrderStatus(orderId,"TIMEOUT");
        }
    }
}
