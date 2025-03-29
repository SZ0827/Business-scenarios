package com.sz.consumer;

import com.sz.config.NotificationWebSocketHandler;
import com.sz.entity.Notification;
import com.sz.repository.NotificationRepository;
import com.sz.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final NotificationWebSocketHandler webSocketHandler;
    private final SmsService smsService;
    @RabbitListener(queues = "notification.queue")
    public void receiveNotification(Notification notification){
        notificationRepository.save(notification);
        try{
        webSocketHandler.sendNotification(notification.getUserId(), notification.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        // 短信通知（假设用户手机号存储在数据库）
        String phoneNumber = getUserPhoneNumber(notification.getUserId());
        if (phoneNumber != null) {
            smsService.sendSms(phoneNumber, notification.getMessage());
        }

        System.out.println("收到通知：" + notification.getMessage());
    }

    private String getUserPhoneNumber(Long userId) {
        // 这里假设用户手机号存储在数据库，实际情况应查询数据库
        return "13800000000";
    }
    }

