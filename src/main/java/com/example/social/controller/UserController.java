package com.example.social.controller;

import com.example.social.dto.LoginResponse;
import com.example.social.models.User;
import com.example.social.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.social.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserService us;

    @Autowired
    JwtService jwtService;
    @PostMapping("/register")
    public User register(@RequestBody User u){
        return us.register(u);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody User u) {

        User user = us.login(u);

        if (user.getUser_id() == null) {
            return new LoginResponse(
                    new User(),
                    null
            );
        }

        String token =
                jwtService.generateToken(
                        user.getUsername()
                );

        return new LoginResponse(
                user,
                token
        );
    }
    @PostMapping(value = "/updateuserdetails", consumes = "multipart/form-data")
    public User update(
            @RequestPart("user") User u,
            @RequestPart(value = "profile_pic", required = false) MultipartFile media
    ) throws Exception {

        return us.updateUser(u, media);
    }
    @GetMapping("/profile")
    public User getByUsername(@RequestParam String username){
        return us.getUserByUsername(username);
    }
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam String q
    ) {
        return us.searchUsers(q);
    }
}
