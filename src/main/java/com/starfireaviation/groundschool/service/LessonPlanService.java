package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.repository.LessonPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LessonPlanService extends BaseService {

    @Autowired
    private LessonPlanRepository lessonPlanRepository;

    public List<LessonPlan> getAll() {
        return lessonPlanRepository.findAll();
    }

    public LessonPlan createOrUpdate(final LessonPlan lessonPlan) {
        return lessonPlanRepository.save(lessonPlan);
    }

    public void removeLessonPlan(final Long lessonPlanId) {
        lessonPlanRepository
                .findById(lessonPlanId)
                .ifPresent(lessonPlan -> lessonPlanRepository.delete(lessonPlan));
    }

    public LessonPlan getLessonPlan(final Long lessonPlanId) {
        return lessonPlanRepository.findById(lessonPlanId).orElse(null);
    }
}
