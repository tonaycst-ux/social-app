package com.example.social.repository;

import com.example.social.models.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepo extends JpaRepository<Friends,Integer> {
    @Query("""
    SELECT f
    FROM Friends f
    WHERE (f.user1.user_id = :uid OR f.user2.user_id = :uid)
      AND f.status = :status
""")
    public List<Friends> getFriends(@Param("uid") Integer uid , @Param("status") Friends.Status status);

    @Query("""
    SELECT f
    FROM Friends f
    WHERE f.user2.user_id = :uid
      AND f.status = :status
""")
    public List<Friends> getPendingRequests(
            @Param("uid") Integer uid,
            @Param("status") Friends.Status status
    );
}
