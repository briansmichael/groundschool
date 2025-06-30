package com.starfireaviation.groundschool.quiz;

import com.starfireaviation.groundschool.questions.QuestionValidator;
import org.springframework.context.ApplicationEventPublisher;
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
@RequestMapping("/quiz")
class QuizController {

    private final QuizService quizService;

    QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    void create(@RequestBody Quiz quiz) throws Exception {
        this.quizService.create(quiz);
    }
}

@Service
@Transactional
class QuizService {

    private final ApplicationEventPublisher publisher;

    private final QuestionValidator questionValidator;

    private final QuizRepository quizRepository;

    QuizService(ApplicationEventPublisher publisher, QuestionValidator questionValidator, QuizRepository repository) {
        this.publisher = publisher;
        this.questionValidator = questionValidator;
        this.quizRepository = repository;
    }

    void create(Quiz quiz) throws Exception {
        for (var qId : quiz.questionIds())
            questionValidator.exists(qId);
        var saved = this.quizRepository.save(quiz);
        System.out.println("saved [" + saved + "]");
    }

    void start(Quiz quiz) {
        System.out.println("started [" + quiz + "]");

        this.publisher.publishEvent(new QuizStartedEvent(quiz.id()));
    }

    void complete(Quiz quiz) {
        System.out.println("completed [" + quiz + "]");

        this.publisher.publishEvent(new QuizCompletedEvent(quiz.id()));
    }
}

interface QuizRepository extends ListCrudRepository<Quiz, Long> {
}

@Table("quizzes")
record Quiz(@Id Long id, Set<Integer> questionIds) {
}
