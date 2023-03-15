package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.model.entity.LessonPlanLesson;
import com.starfireaviation.groundschool.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<List<Lesson>> findAllByChapterId(Long chapterId);

    Optional<Lesson> findByReferenceId(Long referenceId);

}
