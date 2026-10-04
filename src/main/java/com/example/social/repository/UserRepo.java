package com.example.social.repository;

import com.example.social.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    @Query("select p from User p where p.username=:name" )
        public User findByUsername(@Param("name") String name);
    @Query("select u from User u where u.user_id=:uid ")
    public Optional<User> findById(@Param("uid") Integer uid);



        @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR u.phone LIKE CONCAT('%', :query, '%')
    """)
        List<User> searchUsers(@Param("query") String query);
}

