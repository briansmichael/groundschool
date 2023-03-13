package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.repository.LessonPlanLessonRepository;
import com.starfireaviation.groundschool.model.repository.LessonPlanRepository;
import com.starfireaviation.groundschool.model.repository.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LessonPlanService extends BaseService {

    @Autowired
    private LessonPlanRepository lessonPlanRepository;

    @Autowired
    private LessonPlanLessonRepository lessonPlanLessonRepository;

    @Autowired
    private LessonRepository lessonRepository;

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
        final LessonPlan lessonPlan = lessonPlanRepository.findById(lessonPlanId).orElse(null);
        if (lessonPlan != null) {
            lessonPlan.setLessons(lessonPlanLessonRepository
                    .findAllByLessonPlanId(lessonPlanId)
                    .orElse(new ArrayList<>())
                    .stream()
                    .map(lpl -> lessonRepository.findById(lpl.getLessonId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        return lessonPlan;
    }
}
