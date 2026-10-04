package com.example.social.controller;

import com.example.social.models.Comments;
import com.example.social.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentsService;


    @GetMapping("/getComments")
    public List<Comments> getComments(
            @RequestParam("pid") Integer postId
    ) {
        return commentsService.getComments(postId);
    }


    @PostMapping("/addComment")
    public Comments addComment(
            @RequestParam("pid") Integer postId,
            @RequestParam("uid") Integer userId,
            @RequestParam("content") String content
    ) {
        return commentsService.addComment(
                postId,
                userId,
                content
        );
    }


    @DeleteMapping("/deleteComment")
    public void deleteComment(
            @RequestParam("cid") Integer commentId
    ) {
        commentsService.deleteComment(commentId);
    }
}