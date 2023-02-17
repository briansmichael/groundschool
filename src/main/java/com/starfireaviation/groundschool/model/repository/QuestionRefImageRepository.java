package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.QuestionRefImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRefImageRepository extends JpaRepository<QuestionRefImage, Long> {

    Optional<List<QuestionRefImage>> findAllByQuestionId(Long questionId);
}
