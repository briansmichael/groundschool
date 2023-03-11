package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    Optional<List<QuizQuestion>> findAllByQuizId(Long quizId);

    Optional<List<QuizQuestion>> findAllByQuizIdAndQuestionId(Long quizId, Long questionId);
}
