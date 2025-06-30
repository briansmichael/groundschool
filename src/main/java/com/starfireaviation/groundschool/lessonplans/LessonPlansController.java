package com.starfireaviation.groundschool.lessonplans;

import com.starfireaviation.groundschool.common.ActivityType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/lessonplans")
class LessonPlansController {

    private final LessonPlans lessonPlans;

    LessonPlansController(LessonPlans lessonPlans) {
        this.lessonPlans = lessonPlans;
    }

    @PostMapping
    void create(@RequestBody LessonPlan lessonPlan) {
        this.lessonPlans.create(lessonPlan);
    }
    
    @GetMapping("/{lessonPlanId}")
    LessonPlan get(@PathVariable("lessonPlanId") Long lessonPlanId) {
        return this.lessonPlans.get(lessonPlanId);
    }
    
    @PutMapping("/{lessonPlanId}")
    void update(@PathVariable("lessonPlanId") Long lessonPlanId, @RequestBody LessonPlan lessonPlan) {
        this.lessonPlans.update(lessonPlanId, lessonPlan);
    }
    
    @DeleteMapping("/{lessonPlanId}")
    void delete(@PathVariable("lessonPlanId") Long lessonPlanId) {
        this.lessonPlans.delete(lessonPlanId);
    }
    
    @GetMapping
    List<LessonPlan> find() {
        return this.lessonPlans.find();
    }
}

@Service
@Transactional
class LessonPlans {
    private final LessonPlanRepository lessonPlanRepository;

    LessonPlans(LessonPlanRepository repository) {
        this.lessonPlanRepository = repository;
    }

    void create(LessonPlan lessonPlan) {
        var saved = this.lessonPlanRepository.save(lessonPlan);
        System.out.println("saved [" + saved + "]");
    }

    public LessonPlan get(Long lessonPlanId) {
        return null;
    }

    public void update(Long lessonPlanId, LessonPlan lessonPlan) {
    }

    public void delete(Long lessonPlanId) {
    }

    public List<LessonPlan> find() {
        return new ArrayList<>();
    }
}

interface LessonPlanRepository extends ListCrudRepository<LessonPlan, Long> {
}

@Table("lesson_plans")
record LessonPlan(@Id Long id, Date createdAt, Date updatedAt, String title, String summary, String objective,
                  String content, String schedule, String equipment, String instructorActions, String studentActions,
                  String completionStandards) {
}

@Table("lesson_plan_activity")
record LessonPlanActivity(@Id Long id, Date createdAt, Date updatedAt, Long lessonPlanId, Long activityId) {
}

@Table("activity")
record Activity(@Id Long id, Date createdAt, Date updatedAt, String title, String duration, ActivityType activityType,
                Long referenceId) {
}