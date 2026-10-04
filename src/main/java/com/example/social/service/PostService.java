package com.example.social.service;

import com.example.social.models.Post;
import com.example.social.models.User;
import com.example.social.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.social.repository.PostRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {
    @Autowired
    PostRepo pr;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CloudinaryService cloudinaryService;

    public List<Post> getPostById(Integer ownerid){
        System.out.println(ownerid);
        return pr.getPostsById(ownerid);
    }

    public List<Post> getPostByRelevence(Integer userId) {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime sixHoursAgo =
                now.minusHours(6);

        LocalDateTime oneDayAgo =
                now.minusDays(1);

        LocalDateTime threeDaysAgo =
                now.minusDays(3);

        return pr.getPostByRelevance(
                userId,
                sixHoursAgo,
                oneDayAgo,
                threeDaysAgo
        );
    }

    public Post update(Post p) {

        Post old = pr.findById(p.getPost_id()).orElse(null);

        if (old == null) {
            return null;
        }

        old.setText(p.getText());
        old.setMedia_content(p.getMedia_content());
        old.setTagged(p.getTagged());

        return pr.save(old);
    }

    public Post createPost(
            Integer ownerId,
            String text,
            MultipartFile[] media
    ) throws IOException {

        // Find owner
        User owner = userRepo.findById(ownerId) .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Create Post
        Post post = new Post();

        post.setOwner(owner);
        post.setText(text);
        post.setCreatedAt(LocalDateTime.now());
        post.setShares(0);

        // Upload media
        List<String> mediaUrls = new ArrayList<>();

        if (media != null) {

            for (MultipartFile file : media) {

                if (file != null && !file.isEmpty()) {

                    String url =
                            cloudinaryService.upload(file);

                    mediaUrls.add(url);
                }
            }
        }

        post.setMedia_content(mediaUrls);

        return pr.save(post);
    }
    public Post getaPostById(Integer id){
        return pr.findById(id).orElse(null);
    }
}
