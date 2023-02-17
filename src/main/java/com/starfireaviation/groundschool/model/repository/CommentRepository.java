package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
