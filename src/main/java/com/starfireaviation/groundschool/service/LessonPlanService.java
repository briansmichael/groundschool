package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LessonPlanService extends BaseService {

    @Autowired
    private LessonService lessonService;

    public List<LessonPlan> getAll() {
        final List<LessonPlan> lessonPlans = new ArrayList<>();
        return lessonPlans;
    }

    public LessonPlan createOrUpdate(final LessonPlan lessonPlan) {
        return lessonPlan;
    }

    public void addLessonPlanLesson(final Long lessonPlanId, final Long lessonId) {
    }

    public void removeLessonPlanLesson(final Long lessonPlanId, final Long lessonId) {
    }

    public void removeLessonPlan(final Long lessonPlanId) {
    }

    public LessonPlan getLessonPlan(final Long lessonPlanId) {
        return new LessonPlan();
    }
}
