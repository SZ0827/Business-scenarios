package com.sz.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQconfig {
    @Bean
    public Queue notificationQueue(){
        return new Queue("notification.queue",true);
    }
}
