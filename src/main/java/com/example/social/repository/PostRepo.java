package com.example.social.repository;

import com.example.social.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.owner.user_id = :owner")
    List<Post> getPostsById(@Param("owner") Integer owner);


    @Query("""
    SELECT p
    FROM Post p
    WHERE p.owner.user_id <> :userId

    ORDER BY
    (
        CASE
            WHEN EXISTS (
                SELECT c
                FROM Comments c
                WHERE c.userId.user_id = :userId
                  AND c.postId.post_id = p.post_id
            )
            THEN 2
            ELSE 0
        END

        +

        CASE
            WHEN p.createdAt >= :sixHoursAgo
                THEN 5

            WHEN p.createdAt >= :oneDayAgo
                THEN 3

            WHEN p.createdAt >= :threeDaysAgo
                THEN 1

            ELSE 0
        END

        +

        p.shares
    ) DESC,

    p.createdAt DESC
    """)
    List<Post> getPostByRelevance(
            @Param("userId") Integer userId,
            @Param("sixHoursAgo") LocalDateTime sixHoursAgo,
            @Param("oneDayAgo") LocalDateTime oneDayAgo,
            @Param("threeDaysAgo") LocalDateTime threeDaysAgo
    );
}