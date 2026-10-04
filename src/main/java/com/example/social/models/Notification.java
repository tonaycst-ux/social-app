package com.example.social.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    public enum NotificationType{
        MESSAGE,
        FRIEND_REQUEST,
        FRIEND_REQUEST_ACCEPTED,
        LIKE,
        COMMENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    private Integer referenceId;

    private boolean seen = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}
