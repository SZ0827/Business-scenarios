package com.sz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqCofig {
    @Bean
    public Queue paymentQueue() {
        return new Queue("payment.callback.queue", true);
    }

    @Bean
    public Queue orderDelayQueue() {
        return new Queue("order.delay.queue", true);
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.delay.exchange");
    }

    @Bean
    public Binding orderDelayBinding(Queue orderDelayQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderDelayQueue).to(orderExchange).with("order.delay.routing");
    }
}
