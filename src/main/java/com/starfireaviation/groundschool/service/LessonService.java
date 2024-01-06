package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Lesson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LessonService extends BaseService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChapterService chapterService;

    public Lesson createOrUpdate(final Lesson lesson) {
        return lesson;
    }

    public void removeLesson(final Long lessonId) {
    }

    public List<Lesson> getLessonsForChapter(final Long chapterId) {
        final List<Lesson> lessons = new ArrayList<>();
        return lessons;
    }

    public Lesson getLesson(final Long lessonId) {
        return new Lesson();
    }

}
