package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.repository.LessonPlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LessonPlanService extends BaseService {

    @Autowired
    private LessonPlanRepository lessonPlanRepository;

    public LessonPlan getLessonPlan(final Long lessonPlanId) {
        return lessonPlanRepository.findById(lessonPlanId).orElse(null);
    }
}
