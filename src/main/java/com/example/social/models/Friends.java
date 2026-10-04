package com.example.social.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer frndId;
    public enum Status{
        Accepted,
        pending,
        Rejected
    }
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;
    private Status status;

}
