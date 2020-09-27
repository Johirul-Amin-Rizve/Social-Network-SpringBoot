package com.goruslan.socialgeeking.repository;

import com.goruslan.socialgeeking.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
