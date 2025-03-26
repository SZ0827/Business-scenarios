package com.sz.service;

import com.sz.entity.Order;
import com.sz.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final RedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    @Transactional
    public Long createOrder(Long userId, Long productId, int amount){
        Order order=new Order(null,userId,productId,amount,"PENDING",null);
        orderMapper.createOrder(order);
        redisTemplate.opsForValue().set("order:"+order.getId(),order);
        rabbitTemplate.convertAndSend("order.create",order);
        return order.getId();
    }
    @Transactional
    public void updataOrderStatus(Long orderId,String status){
        orderMapper.updateOrderStatus(orderId,status);
    }
}
