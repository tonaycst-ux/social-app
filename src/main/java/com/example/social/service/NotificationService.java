
package com.example.social.service;

import com.example.social.models.Notification;
import com.example.social.models.User;
import com.example.social.repository.NotificationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public Notification createNotification(
            User receiver,
            User sender,
            Notification.NotificationType type,
            String message,
            Integer referenceId
    ) {
        Notification notification = new Notification();

        notification.setReceiver(receiver);
        notification.setSender(sender);
        notification.setType(type);
        notification.setMessage(message);
        notification.setReferenceId(referenceId);
        notification.setSeen(false);

        return notificationRepo.save(notification);
    }

    public List<Notification> getNotifications(User receiver) {
        return notificationRepo
                .findByReceiverOrderByCreatedAtDesc(receiver);
    }

    public List<Notification> getUnreadNotifications(User receiver) {
        return notificationRepo
                .findByReceiverAndSeenFalseOrderByCreatedAtDesc(receiver);
    }

    public Integer getUnreadCount(User receiver) {
        return notificationRepo
                .countByReceiverAndSeenFalse(receiver);
    }

    public Notification markAsSeen(Integer notificationId) {
        Notification notification = notificationRepo
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found")
                );

        notification.setSeen(true);

        return notificationRepo.save(notification);
    }

    public void markAllAsSeen(User receiver) {
        List<Notification> notifications =
                notificationRepo.findByReceiverOrderByCreatedAtDesc(receiver);

        for (Notification notification : notifications) {
            notification.setSeen(true);
        }

        notificationRepo.saveAll(notifications);
    }

    public void deleteNotification(Integer notificationId) {
        notificationRepo.deleteById(notificationId);
    }
}

