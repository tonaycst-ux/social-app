package com.example.social.repository;

import com.example.social.models.Like;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Like,Integer> {
    @Query("select l from Like l where l.post.post_id=:pid")
    public List<Like> getLikeByPost(@Param("pid") Integer pid);

    @Modifying
    @Transactional
    @Query("delete from Like l where l.post.post_id=:pid and l.owner.user_id=:uid")
    public Like getLikeByPostandUser(@Param("pid") Integer pid,@Param("uid") Integer uid);
}
