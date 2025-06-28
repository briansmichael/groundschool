package com.starfireaviation.groundschool.questions;

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

import java.util.Set;

@Controller
@ResponseBody
@RequestMapping("/questions")
class QuestionsController {

    private final Questions questions;

    QuestionsController(Questions questions) {
        this.questions = questions;
    }

    @PostMapping
    void create(@RequestBody Question question) {
        this.questions.create(question);
    }
}

@Service
@Transactional
class Questions {
    private final QuestionRepository questionRepository;

    Questions(QuestionRepository repository) {
        this.questionRepository = repository;
    }

    void create(Question question) {
        var saved = this.questionRepository.save(question);
        System.out.println("saved [" + saved + "]");
    }

    boolean exists(Integer id) {
        return this.questionRepository.existsById(id);
    }
}

interface QuestionRepository extends ListCrudRepository<Question, Integer> {
}

@Table("questions")
record Question(@Id Integer id, Set<Answer> answers) {
}

@Table("answers")
record Answer(@Id Integer id) {
}
