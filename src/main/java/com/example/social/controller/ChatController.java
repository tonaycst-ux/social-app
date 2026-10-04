package com.example.social.controller;

import io.getstream.models.UpdateUsersRequest;
import io.getstream.models.UserRequest;
import org.springframework.web.bind.annotation.*;

import io.getstream.services.framework.StreamSDKClient;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ChatController {

    private final StreamSDKClient streamClient;

    public ChatController(StreamSDKClient streamClient) {
        this.streamClient = streamClient;
    }

    @GetMapping("/api/chat/token")
    public String getChatToken(@RequestParam String userId) {

        return streamClient
                .tokenBuilder()
                .createToken(userId);
    }
    @PostMapping("/api/chat/user")
    public String createStreamUser(
            @RequestParam String userId,
            @RequestParam String name
    ) throws Exception{

        UserRequest user = UserRequest.builder()
                .id(userId)
                .name(name)
                .build();

        UpdateUsersRequest request = UpdateUsersRequest.builder()
                .users(Map.of(userId, user))
                .build();

        streamClient
                .updateUsers(request)
                .execute();

        return "Stream user created: " + userId;
    }

}