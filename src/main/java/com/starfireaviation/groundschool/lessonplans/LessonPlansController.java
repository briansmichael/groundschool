package com.starfireaviation.groundschool.lessonplans;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

interface LessonPlanRepository extends ListCrudRepository<LessonPlan, Integer> {
}

@Table("lesson_plans")
record LessonPlan(@Id Integer id) {
}
