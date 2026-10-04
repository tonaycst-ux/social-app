
package com.example.social.controller;

import com.example.social.models.Notification;
import com.example.social.models.User;
import com.example.social.repository.UserRepo;
import com.example.social.service.NotificationService;
import com.example.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;
    @Autowired
    private UserRepo userRepo;

    public NotificationController(
            NotificationService notificationService,
            UserService userService
    ) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(
            @PathVariable Integer userId
    ) {
        User user = userRepo.findById(userId).orElse(null);

        return notificationService.getNotifications(user);
    }

    @GetMapping("/{userId}/unread")
    public List<Notification> getUnreadNotifications(
            @PathVariable Integer userId
    ) {
        User user = userRepo.findById(userId).orElse(null);

        return notificationService.getUnreadNotifications(user);
    }

    @GetMapping("/{userId}/count")
    public Integer getUnreadCount(
            @PathVariable Integer userId
    ) {
        User user = userRepo.findById(userId).orElse(null);

        return notificationService.getUnreadCount(user);
    }

    @PutMapping("/{notificationId}/seen")
    public Notification markAsSeen(
            @PathVariable Integer notificationId
    ) {
        return notificationService.markAsSeen(notificationId);
    }

    @PutMapping("/{userId}/seen-all")
    public void markAllAsSeen(
            @PathVariable Integer userId
    ) {
        User user = userRepo.findById(userId).orElse(null);

        notificationService.markAllAsSeen(user);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(
            @PathVariable Integer notificationId
    ) {
        notificationService.deleteNotification(notificationId);
    }
}

