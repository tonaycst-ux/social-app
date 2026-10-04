package com.example.social.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer LikeId;
    @ManyToOne
    private User owner;
    @ManyToOne
    private User reciever;
    @ManyToOne
    private Post post;
    private LocalDateTime createdat;
}
