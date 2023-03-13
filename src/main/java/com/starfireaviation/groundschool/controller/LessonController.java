package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@Slf4j
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/lessons")
    public String getLessons(final Model model) {
        return lessonsView(model);
    }

    @GetMapping("/lessons/{id}")
    public String getLessonPlan(@PathVariable("id") final Long lessonId, final Model model) {
        if (lessonId == null) {
            return lessonsView(model);
        }
        try {
            return lessonView(lessonId, model);
        } catch (Exception e) {
            return lessonsView(model);
        }
    }

    @GetMapping("/lessons/edit/{id}")
    public String editLesson(@PathVariable("id") final Long lessonId, final Model model) {
        log.info("Editing Lesson.");
        model.addAttribute("lesson", lessonService.getLesson(lessonId));
        log.info("Returning lesson id: {}", lessonId);
        return "editlesson"; //view
    }

    @GetMapping("/lessons/new")
    public String newLesson(final Model model) {
        log.info("Creating a new Lesson.");
        model.addAttribute("lesson", new Lesson());
        return "newlesson"; //view
    }

    @PostMapping("/lessons")
    public String createLesson(final Lesson lesson, final Model model) {
        log.info("Creating Lesson.");
        lessonService.createOrUpdate(lesson);
        return lessonView(lesson.getId(), model);
    }

    @PutMapping("/lessons")
    public String updateLesson(final Lesson lesson, final Model model) {
        log.info("Updating Lesson.");
        lessonService.createOrUpdate(lesson);
        return lessonView(lesson.getId(), model);
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") final Long lessonId, final Model model) {
        log.info("Deleting Lesson.");
        lessonService.removeLesson(lessonId);
        return lessonsView(model);
    }

    private String lessonsView(final Model model) {
        final List<Lesson> lessons = lessonService.getAll();
        model.addAttribute("lessons", lessons);
        log.info("Returning lessons");
        return "lessons"; //view
    }

    private String lessonView(final Long lessonId, final Model model) {
        model.addAttribute("lesson", lessonService.getLesson(lessonId));
        log.info("Returning lesson id: {}", lessonId);
        return "lesson"; //view
    }

}
