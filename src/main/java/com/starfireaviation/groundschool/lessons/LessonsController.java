package com.starfireaviation.groundschool.lessons;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/lessons")
class LessonsController {

    private final Lessons lessons;

    LessonsController(Lessons lessons) {
        this.lessons = lessons;
    }

    @PostMapping
    void create(@RequestBody Lesson lesson) {
        this.lessons.create(lesson);
    }

    @GetMapping("/{lessonId}")
    Lesson get(@PathVariable("lessonId") Long lessonId) {
        return this.lessons.get(lessonId);
    }

    @PutMapping("/{lessonId}")
    void update(@PathVariable("lessonId") Long lessonId, @RequestBody Lesson lesson) {
        this.lessons.update(lessonId, lesson);
    }

    @DeleteMapping("/{lessonId}")
    void delete(@PathVariable("lessonId") Long lessonId) {
        this.lessons.delete(lessonId);
    }

    @GetMapping
    List<Lesson> find() {
        return this.lessons.find();
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

    public Lesson get(Long lessonId) {
        return null;
    }

    public void update(Long lessonId, Lesson lesson) {
    }

    public void delete(Long lessonId) {
    }

    public List<Lesson> find() {
        return new ArrayList<>();
    }
}

interface LessonRepository extends ListCrudRepository<Lesson, Long> {
}

@Table("lessons")
record Lesson(@Id Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String group, String chapter,
              String title, String text, boolean required) {
}
