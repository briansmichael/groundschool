package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.model.repository.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LessonService extends BaseService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    public Lesson createOrUpdate(final Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void removeLesson(final Long lessonId) {
        lessonRepository
                .findById(lessonId)
                .ifPresent(lesson -> lessonRepository.delete(lesson));
    }

    public Lesson getLesson(final Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }
}
