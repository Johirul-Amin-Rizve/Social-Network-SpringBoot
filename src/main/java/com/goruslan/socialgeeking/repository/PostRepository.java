package com.goruslan.socialgeeking.repository;

import com.goruslan.socialgeeking.domain.Post;
import com.goruslan.socialgeeking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByPrivacy(String privacy);

    List<Post> findAllByPrivacyAndUser(String privacy, User user);

    @Query("SELECT p FROM Post p WHERE p.privacy = ?1 and p.user <> ?2")
    List<Post> findAllByPrivacyAndNotUser(String privacy, User user);

    List<Post> findAllByUser(User user);

}
