package com.example.social.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public enum Gender {
        Male,
        Female
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profile_pic;
    private String About;
    private LocalDate dob;
    private String location;

    @ElementCollection
    private List<String> interest = new ArrayList<>();

    @ElementCollection
    private List<String> skills = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String siteurl;
    private String phone;
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Post> posts = new ArrayList<>();
}