package com.example.social.repository;

import com.example.social.models.Notification;
import com.example.social.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    List<Notification> findByReceiverOrderByCreatedAtDesc(User receiver);

    List<Notification> findByReceiverAndSeenFalseOrderByCreatedAtDesc(User receiver);

    Integer countByReceiverAndSeenFalse(User receiver);
}