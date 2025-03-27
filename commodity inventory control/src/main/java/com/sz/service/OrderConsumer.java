package com.sz.service;

import com.sz.entity.Stock;
import com.sz.repository.StockRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    @Autowired
    private StockService stockService;
    private static final String QUEUE_NAME = "order_queue";
//Spring提供监听装置，监听指定RabbitMQ队列，当队列有消息触发消费者方法
    @RabbitListener(queues = QUEUE_NAME)
    public void processOrder(Long productId) {
        stockService.confirmStock(productId);
    }

}
