package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.entity.Quiz;
import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.QuestionService;
import com.starfireaviation.groundschool.service.QuizService;
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
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    public String getQuiz(@PathParam("quizId") final Long quizId, final Model model) {
        try {
            final List<Quiz> quizzes = quizService.getAll();
            model.addAttribute("quizzes", quizzes);
            final List<Question> questions = quizService.getQuestionsForQuiz(quizId);
            model.addAttribute("questions", questions);
            return quizView(quizId, model);
        } catch (Exception e) {
            return quizView(1L, model);
        }
    }

    @PostMapping("/quizzes")
    public String createQuiz(final Quiz quiz, final Model model) {
        final Quiz savedQuiz = quizService.createOrUpdate(quiz);
        return quizView(savedQuiz.getId(), model);
    }

    @PutMapping("/quizzes")
    public String updateQuiz(final Quiz quiz, final Model model) {
        final Quiz savedQuiz = quizService.createOrUpdate(quiz);
        return quizView(savedQuiz.getId(), model);
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

    private String quizView(final Long quizId, final Model model) {
        final List<Quiz> quizzes = quizService.getAll();
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("quiz", quizService.getQuiz(quizId));
        final Selection selection = new Selection();
        selection.setQuizId(quizId);
        model.addAttribute("selection", selection);
        return "quiz"; //view
    }

}
