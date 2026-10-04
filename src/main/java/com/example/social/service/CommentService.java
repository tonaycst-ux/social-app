package com.example.social.service;

import com.example.social.models.Comments;
import com.example.social.models.Notification;
import com.example.social.models.Post;
import com.example.social.models.User;
import com.example.social.repository.CommentRepo;
import com.example.social.repository.CommentRepo;
import com.example.social.repository.PostRepo;
import com.example.social.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    NotificationService notificationService;
    public List<Comments> getComments(Integer postId) {
        return commentsRepo.getCommentsByPost(postId);
    }


    public Comments addComment(
            Integer postId,
            Integer userId,
            String content
    ) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Comments comment = new Comments();

        comment.setPostId(post);
        comment.setUserId(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDate.now());

        notificationService.createNotification(
                post.getOwner(),
                user,
                Notification.NotificationType.COMMENT,
                user.getName() + " commented on your post",
                post.getPost_id()

        );
        return commentsRepo.save(comment);
    }


    public void deleteComment(Integer commentId) {
        commentsRepo.deleteById(commentId);
    }
}