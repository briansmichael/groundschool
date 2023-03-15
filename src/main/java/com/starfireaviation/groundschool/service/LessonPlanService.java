package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.entity.LessonPlanLesson;
import com.starfireaviation.groundschool.model.repository.LessonPlanLessonRepository;
import com.starfireaviation.groundschool.model.repository.LessonPlanRepository;
import com.starfireaviation.groundschool.model.repository.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    private LessonService lessonService;

    public List<LessonPlan> getAll() {
        return lessonPlanRepository.findAll();
    }

    public LessonPlan createOrUpdate(final LessonPlan lessonPlan) {
        return lessonPlanRepository.save(lessonPlan);
    }

    public void addLessonPlanLesson(final Long lessonPlanId, final Long lessonId) {
        final Optional<List<LessonPlanLesson>> lessonPlanLessonsOpt =
                lessonPlanLessonRepository.findAllByLessonPlanId(lessonPlanId);
        if (lessonPlanLessonsOpt.isPresent()) {
            final List<LessonPlanLesson> lessonPlanLessons = lessonPlanLessonsOpt.get();
            if (lessonPlanLessons.stream().anyMatch(lpl -> Objects.equals(lpl.getLessonId(), lessonId))) {
                log.info("Lesson ID: {} is already assigned to Lesson Plan ID: {}", lessonId, lessonPlanId);
                return;
            }
        }
        final Optional<LessonPlan> lessonPlanOpt = lessonPlanRepository.findById(lessonPlanId);
        if (lessonPlanOpt.isPresent()) {
            final Lesson lesson = lessonService.getLesson(lessonId);
            if (lesson != null) {
                final LessonPlanLesson lessonPlanLesson = new LessonPlanLesson();
                lessonPlanLesson.setLessonPlanId(lessonPlanId);
                lessonPlanLesson.setLessonId(lessonId);
                log.info("Assigning Lesson ID: {} to Lesson Plan ID: {}", lessonId, lessonPlanId);
                lessonPlanLessonRepository.save(lessonPlanLesson);
            } else {
                log.warn("No lesson found for ID: {}", lessonId);
            }
        } else {
            log.warn("No lesson plan found for ID: {}", lessonPlanId);
        }
    }

    public void removeLessonPlanLesson(final Long lessonPlanId, final Long lessonId) {
        lessonPlanLessonRepository
                .findAllByLessonPlanIdAndLessonId(lessonPlanId, lessonId)
                .ifPresent(lessonPlanLessons -> lessonPlanLessonRepository.deleteAll(lessonPlanLessons));
    }

    public void removeLessonPlan(final Long lessonPlanId) {
        lessonPlanLessonRepository
                .findAllByLessonPlanId(lessonPlanId)
                .ifPresent(lpl -> lessonPlanLessonRepository.deleteAll(lpl));
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
