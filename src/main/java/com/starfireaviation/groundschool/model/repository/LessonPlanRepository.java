package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {
}
