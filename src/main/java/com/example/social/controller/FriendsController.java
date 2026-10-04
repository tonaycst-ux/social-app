package com.example.social.controller;

import com.example.social.models.Friends;
import com.example.social.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class FriendsController {
    @Autowired
    FriendsService friendsService;
    @GetMapping("/friends")
    public List<Friends> getFriends(@RequestParam("uid") Integer uid){
        return friendsService.getFriends(uid);
    }

    @GetMapping("/requests")
    public List<Friends> getRequests(@RequestParam("uid") Integer uid){
        return friendsService.getPendingRequest(uid);
    }

    @GetMapping("/accept")
    public Friends accept(@RequestParam("fid") Integer fid){
        return friendsService.accept(fid);
    }

    @GetMapping("/reject")
    public Friends reject(@RequestParam("fid") Integer fid){
        return friendsService.reject(fid);
    }
    @PostMapping("/request")
    public Friends request(@RequestBody Friends f){
        f.setStatus(Friends.Status.pending);
        return friendsService.sendRequest(f);
    }
}
