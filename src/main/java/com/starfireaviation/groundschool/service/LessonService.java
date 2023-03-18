package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Chapter;
import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.repository.LessonPlanLessonRepository;
import com.starfireaviation.groundschool.model.repository.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LessonService extends BaseService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonPlanLessonRepository lessonPlanLessonRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ChapterService chapterService;

    public Lesson createOrUpdate(final Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void removeLesson(final Long lessonId) {
        lessonPlanLessonRepository
                .findAllByLessonId(lessonId)
                .ifPresent(lpl -> lessonPlanLessonRepository.deleteAll(lpl));
        lessonRepository
                .findById(lessonId)
                .ifPresent(lesson -> lessonRepository.delete(lesson));
    }

    public List<Lesson> getLessonsForChapter(final Long chapterId) {
        return lessonRepository.findAllByChapterId(chapterId).orElse(null);
    }

    public Lesson getLesson(final Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }

    public void update() {
        log.info("Updating Lesson info...");
        chapterService.getAllChapterIDs().forEach(chapterId ->
                questionService.getQuestionIDsForChapter(chapterId).forEach(questionId -> {
            final Optional<Lesson> lessonOpt = lessonRepository.findByReferenceId(questionId);
            if (lessonOpt.isEmpty()) {
                final Question question = questionService.getQuestion(questionId);
                if (question != null) {
                    final Chapter chapter = chapterService.getChapter(question.getChapterId());
                    final Lesson lesson = new Lesson();
                    lesson.setTitle(Long.toString(questionId));
                    lesson.setReferenceId(questionId);
                    lesson.setChapterId(question.getChapterId());
                    if (chapter != null) {
                        lesson.setGroupId(chapter.getGroupId());
                    }
                    lesson.setRequired(Boolean.FALSE);
                    lesson.setText(question.getExplanation());
                    lesson.setCreatedDate(new Date());
                    lesson.setUpdatedDate(new Date());
                    lessonRepository.save(lesson);
                }
            }
        }));
        log.info("Lesson update complete.");
    }

}
