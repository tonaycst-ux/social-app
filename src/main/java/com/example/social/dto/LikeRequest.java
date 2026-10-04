package com.example.social.dto;

import lombok.Data;

@Data
public class LikeRequest {

    private Integer owner;
    private Integer userId;
    private Integer postId;
}
