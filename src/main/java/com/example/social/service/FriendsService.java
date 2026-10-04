package com.example.social.service;

import com.example.social.models.Friends;
import com.example.social.models.Notification;
import com.example.social.repository.FriendsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    @Autowired
    FriendsRepo friendsRepo;
    @Autowired
    NotificationService notificationService;

    public List<Friends> getFriends(Integer uid){
        return friendsRepo.getFriends(uid, Friends.Status.Accepted);

    }

    public List<Friends> getPendingRequest(Integer uid){
        return friendsRepo.getPendingRequests(uid, Friends.Status.pending);
    }

    public Friends accept(Integer fid){
        Friends f=friendsRepo.findById(fid).orElse(null);

        f.setStatus(Friends.Status.Accepted);
        Friends friends=friendsRepo.save(f);
        if(friends!=null){
            notificationService.createNotification(
                    f.getUser1(),
                    f.getUser2(),

                    Notification.NotificationType.FRIEND_REQUEST_ACCEPTED,
                    f.getUser2().getName()+" Accepted you request",
                    f.getFrndId()
            );
        }
        return f;
    }

    public Friends reject(Integer fid){
        Friends f=friendsRepo.findById(fid).orElse(null);
        f.setStatus(Friends.Status.Rejected);
        friendsRepo.save(f);
        return f;
    }

    public Friends sendRequest(Friends f){
       return  friendsRepo.save(f);
    }
}
