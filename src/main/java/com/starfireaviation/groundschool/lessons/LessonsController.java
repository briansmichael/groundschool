package com.starfireaviation.groundschool.lessons;

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
@RequestMapping("/lessons")
class LessonPlansController {

    private final Lessons lessons;

    LessonPlansController(Lessons lessons) {
        this.lessons = lessons;
    }

    @PostMapping
    void create(@RequestBody Lesson lesson) {
        this.lessons.create(lesson);
    }
}

@Service
@Transactional
class Lessons {
    private final LessonRepository lessonRepository;

    Lessons(LessonRepository repository) {
        this.lessonRepository = repository;
    }

    void create(Lesson lesson) {
        var saved = this.lessonRepository.save(lesson);
        System.out.println("saved [" + saved + "]");
    }
}

interface LessonRepository extends ListCrudRepository<Lesson, Integer> {
}

@Table("lessons")
record Lesson(@Id Integer id) {
}
