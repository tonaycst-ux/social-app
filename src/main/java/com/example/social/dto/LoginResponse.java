package com.example.social.dto;

import com.example.social.models.User;

public record LoginResponse(
        User user,
        String token
) {
}