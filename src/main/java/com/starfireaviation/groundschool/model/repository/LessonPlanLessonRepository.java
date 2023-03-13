package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.LessonPlanLesson;
import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonPlanLessonRepository extends JpaRepository<LessonPlanLesson, Long> {

    Optional<List<LessonPlanLesson>> findAllByLessonPlanId(Long lessonPlanId);

    Optional<List<LessonPlanLesson>> findAllByLessonId(Long lessonId);

    Optional<List<LessonPlanLesson>> findAllByLessonPlanIdAndLessonId(Long lessonPlanId, Long LessonId);
}
