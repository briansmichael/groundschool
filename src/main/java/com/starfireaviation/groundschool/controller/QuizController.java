package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.LessonPlan;
import com.starfireaviation.groundschool.model.entity.Quiz;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.LessonPlanService;
import com.starfireaviation.groundschool.service.QuizService;
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
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private LessonPlanService lessonPlanService;

    @GetMapping("/quizzes")
    public String getQuizzes(final Model model) {
        return quizzesView(model);
    }

    @GetMapping("/quizzes/{id}")
    public String getQuiz(@PathVariable("id") final Long quizId, final Model model) {
        return quizView(quizId, model);
    }

    @GetMapping("/quizzes/edit/{id}")
    public String editQuiz(@PathVariable("id") final Long quizId, final Model model) {
        log.info("Editing Quiz.");
        model.addAttribute("quiz", quizService.getQuiz(quizId));
        model.addAttribute("lessonPlans", lessonPlanService.getAll());
        log.info("Returning quiz id: {}", quizId);
        return "editquiz"; //view
    }

    @GetMapping("/quizzes/new")
    public String newQuiz(final Model model) {
        log.info("Creating a new Quiz.");
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("lessonPlans", lessonPlanService.getAll());
        return "newquiz"; //view
    }

    @PostMapping("/quizzes")
    public String createQuiz(final Quiz quiz, final Model model) {
        quizService.createOrUpdate(quiz);
        return quizzesView(model);
    }

    @PutMapping("/quizzes")
    public String updateQuiz(final Quiz quiz, final Model model) {
        quizService.createOrUpdate(quiz);
        return quizzesView(model);
    }

    @PostMapping("/quizzes/{quizId}/{questionId}")
    public String addQuestion(@PathVariable("quizId") final Long quizId,
                              @PathVariable("questionId") final Long questionId,
                              final Model model) {
        quizService.addQuizQuestion(quizId, questionId);
        return quizView(quizId, model);
    }

    @DeleteMapping("/quizzes/{quizId}/{questionId}")
    public String deleteQuestion(@PathVariable("quizId") final Long quizId,
                                 @PathVariable("questionId") final Long questionId,
                                 final Model model) {
        quizService.removeQuizQuestion(quizId, questionId);
        return quizView(quizId, model);
    }

    @DeleteMapping("/quizzes/{quizId}")
    public String deleteQuestion(@PathVariable("quizId") final Long quizId,
                                 final Model model) {
        quizService.removeQuiz(quizId);
        return quizzesView(model);
    }

    private String quizzesView(final Model model) {
        final List<LessonPlan> lessonPlans = lessonPlanService.getAll();
        model.addAttribute("lessonPlans", lessonPlans);
        final List<Quiz> quizzes = quizService.getAll();
        model.addAttribute("quizzes", quizzes);
        final Selection selection = new Selection();
        model.addAttribute("selection", selection);
        log.info("Returning quizzes");
        return "quizzes"; //view
    }

    private String quizView(final Long quizId, final Model model) {
        model.addAttribute("lessonPlans", lessonPlanService.getAll());
        model.addAttribute("quiz", quizService.getQuiz(quizId));
        return "quiz"; //view
    }

}
