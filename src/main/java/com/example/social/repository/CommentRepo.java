package com.example.social.repository;

import com.example.social.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Integer> {

    @Query("SELECT c FROM Comments c WHERE c.postId.post_id = :pid")
    List<Comments> getCommentsByPost(@Param("pid") Integer pid);

}