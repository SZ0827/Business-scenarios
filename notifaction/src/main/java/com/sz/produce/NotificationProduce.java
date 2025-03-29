package com.sz.produce;

import com.sz.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProduce {
    private final RabbitTemplate rabbitTemplate;
    public void sendNotification(Notification notification){
        rabbitTemplate.convertAndSend("notification.queue", notification);
    }
}
