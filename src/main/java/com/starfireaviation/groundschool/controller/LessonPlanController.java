package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.service.LessonPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@Slf4j
public class LessonPlanController {

    @Autowired
    private LessonPlanService lessonPlanService;

    @GetMapping("/lessonplans")
    public String getLessonPlans(final Model model) {
        return lessonPlansView(model);
    }

    @GetMapping("/lessonplans/{id}")
    public String getLessonPlan(@PathVariable("id") final Long lessonPlanId, final Model model) {
        if (lessonPlanId == null) {
            return lessonPlansView(model);
        }
        try {
            return lessonPlanView(lessonPlanId, model);
        } catch (Exception e) {
            return lessonPlansView(model);
        }
    }

    @GetMapping("/lessonplans/edit/{id}")
    public String editLessonPlan(@PathVariable("id") final Long lessonPlanId, final Model model) {
        log.info("Editing LessonPlan.");
        model.addAttribute("lessonPlan", lessonPlanService.getLessonPlan(lessonPlanId));
        log.info("Returning lesson plan id: {}", lessonPlanId);
        return "editlessonplan"; //view
    }

    @GetMapping("/lessonplans/new")
    public String newLessonPlan(final Model model) {
        log.info("Creating a new LessonPlan.");
        model.addAttribute("lessonPlan", new LessonPlan());
        return "newlessonplan"; //view
    }

    @PostMapping("/lessonplans/{lessonPlanId}/{lessonId}")
    public String addLesson(@PathVariable("lessonPlanId") final Long lessonPlanId,
                            @PathVariable("lessonId") final Long lessonId) {
        log.info("Adding Lesson ID: {} to Lesson Plan ID: {}", lessonId, lessonPlanId);
        lessonPlanService.addLessonPlanLesson(lessonPlanId, lessonId);
        return "success";
    }

    @DeleteMapping("/lessonplans/{lessonPlanId}/{lessonId}")
    public String deleteLesson(@PathVariable("lessonPlanId") final Long lessonPlanId,
                               @PathVariable("lessonId") final Long lessonId) {
        log.info("Removing Lesson ID: {} from Lesson Plan ID: {}", lessonId, lessonPlanId);
        lessonPlanService.removeLessonPlanLesson(lessonPlanId, lessonId);
        return "success";
    }

    @PostMapping("/lessonplans")
    public String createLessonPlan(final LessonPlan lessonPlan, final Model model) {
        log.info("Creating LessonPlan.");
        lessonPlanService.createOrUpdate(lessonPlan);
        return lessonPlanView(lessonPlan.getId(), model);
    }

    @PutMapping("/lessonplans")
    public String updateLessonPlan(final LessonPlan lessonPlan, final Model model) {
        log.info("Updating LessonPlan.");
        lessonPlanService.createOrUpdate(lessonPlan);
        return lessonPlanView(lessonPlan.getId(), model);
    }

    @GetMapping("/lessonplans/delete/{id}")
    public String deleteLessonPlan(@PathVariable("id") final Long lessonPlanId, final Model model) {
        log.info("Deleting LessonPlan.");
        lessonPlanService.removeLessonPlan(lessonPlanId);
        return lessonPlansView(model);
    }

    private String lessonPlansView(final Model model) {
        final List<LessonPlan> lessonPlans = lessonPlanService.getAll();
        model.addAttribute("lessonPlans", lessonPlans);
        log.info("Returning lesson plans");
        return "lessonplans"; //view
    }

    private String lessonPlanView(final Long lessonPlanId, final Model model) {
        model.addAttribute("lessonPlan", lessonPlanService.getLessonPlan(lessonPlanId));
        log.info("Returning lesson plan id: {}", lessonPlanId);
        return "lessonplan"; //view
    }

}
