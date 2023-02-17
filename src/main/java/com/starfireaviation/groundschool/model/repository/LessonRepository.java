package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
