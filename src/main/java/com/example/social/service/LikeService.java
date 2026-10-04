package com.example.social.service;

import com.example.social.dto.LikeRequest;
import com.example.social.models.Like;
import com.example.social.models.Notification;
import com.example.social.models.Post;
import com.example.social.models.User;
import com.example.social.repository.LikeRepo;
import com.example.social.repository.PostRepo;
import com.example.social.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikeService {
    @Autowired
    LikeRepo likes;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PostRepo postRepo;

    @Autowired
    NotificationService notificationService;

    public List<Like> getLikesByPost(Integer pid){
        return likes.getLikeByPost(pid);
    }

    public Like createLike(LikeRequest request) {

        User owner = userRepo.findById(request.getOwner())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        User reciever = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Post post = postRepo.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = new Like();

        like.setOwner(owner);
        like.setReciever(reciever);
        like.setPost(post);
        like.setCreatedat(LocalDateTime.now());

        Like finallike=likes.save(like);
        if(finallike!=null){
            notificationService.createNotification(
                    reciever,
                    owner,
                    Notification.NotificationType.LIKE,
                    owner.getName()+" Liked your  post",
                    post.getPost_id()
            );
        }
        return finallike;
    }

    public Like unlike(Integer pid,Integer uid){
        Like like=likes.getLikeByPostandUser(pid,uid);
        if(like!=null){
            likes.delete(like);
        }
        return null;
    }


}
