package com.sz.controller;

import com.sz.entity.Notification;
import com.sz.produce.NotificationProduce;
import com.sz.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationProduce notificationProduce;
    private final NotificationRepository notificationRepository;
    @PostMapping("/send")
    public String sendNotification(@RequestBody Notification notification){
        notificationProduce.sendNotification(notification);
        return "Notification sent successfully";
    }
    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
