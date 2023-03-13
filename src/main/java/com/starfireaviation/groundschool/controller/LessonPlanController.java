package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.LessonPlanService;
import jakarta.websocket.server.PathParam;
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
    public String getLessonPlan(@PathParam("lessonPlanId") final Long lessonPlanId, final Model model) {
        if (lessonPlanId == null) {
            return lessonPlansView(model);
        }
        try {
            return lessonPlanView(lessonPlanId, model);
        } catch (Exception e) {
            return lessonPlansView(model);
        }
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

    @DeleteMapping("/lessonplans/{lessonPlanId}")
    public String deleteLessonPlan(@PathVariable("lessonPlanId") final Long lessonPlanId, final Model model) {
        log.info("Deleting LessonPlan.");
        lessonPlanService.removeLessonPlan(lessonPlanId);
        return lessonPlansView(model);
    }

    private String lessonPlansView(final Model model) {
        final List<LessonPlan> lessonPlans = lessonPlanService.getAll();
        model.addAttribute("lessonPlans", lessonPlans);
        model.addAttribute("lessonPlan", new LessonPlan());
        final Selection selection = new Selection();
        model.addAttribute("selection", selection);
        log.info("Returning lesson plans");
        return "lessonplans"; //view
    }

    private String lessonPlanView(final Long lessonPlanId, final Model model) {
        model.addAttribute("lessonPlan", lessonPlanService.getLessonPlan(lessonPlanId));
        log.info("Returning lesson plan id: {}", lessonPlanId);
        return "lessonplan"; //view
    }

}
