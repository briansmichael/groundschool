package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
