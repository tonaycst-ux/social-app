package com.example.social.controller;

import com.example.social.dto.LikeRequest;
import com.example.social.models.Like;
import com.example.social.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LikeController {
    @Autowired
    LikeService likeService;

    @GetMapping("/likes")
    public List<Like> getLikesByPosts(@RequestParam("pid") Integer pid){
        return likeService.getLikesByPost(pid);
    }

    @PostMapping("/like")
    public Like createLike(@RequestBody LikeRequest request) {

        return likeService.createLike(request);
    }
    @GetMapping("/test-like")
    public String cheek(){
        System.out.println("HEllo");
        return "hello";
    }

    @GetMapping("/unlike")
    public Like unlike(@RequestParam("pid") Integer pid,@RequestParam("uid") Integer uid){
        return likeService.unlike(pid,uid);
    }
}
