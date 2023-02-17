package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<List<Answer>> findAllByQuestionId(Long questionId);
}
