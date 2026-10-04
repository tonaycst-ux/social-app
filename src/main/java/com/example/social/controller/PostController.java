package com.example.social.controller;

import com.example.social.models.Post;
import com.example.social.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostService ps;

    @GetMapping("/getpostsbyid")
    public List<Post> getposts(@RequestParam("id") Integer Id){
        System.out.println("hi"+Id);
        return ps.getPostById(Id);
    }
    @GetMapping("/posts")
    public List<Post> postsByRelevence(@Param("id")Integer id){
        return ps.getPostByRelevence(id);
    }


    @PostMapping(
            value = "/createPost",
            consumes = "multipart/form-data"
    )
    public Post createPost(

            @RequestParam("ownerId")
            Integer ownerId,

            @RequestParam(value = "text", required = false)
            String text,

            @RequestParam(value = "media", required = false)
            MultipartFile[] media

    ) throws Exception {

        return ps.createPost(
                ownerId,
                text,
                media
        );
    }
    @GetMapping("/getPost")
    public Post getpost(@RequestParam("pid") Integer pid){
        return ps.getaPostById(pid);
    }
}
