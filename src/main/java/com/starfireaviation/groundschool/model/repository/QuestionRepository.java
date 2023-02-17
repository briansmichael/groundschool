package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Answer;
import com.starfireaviation.groundschool.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<List<Question>> findAllByChapterId(Long chapterId);
}
