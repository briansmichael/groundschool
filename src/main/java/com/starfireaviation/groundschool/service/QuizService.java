package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.entity.Quiz;
import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import com.starfireaviation.groundschool.model.repository.QuizQuestionRepository;
import com.starfireaviation.groundschool.model.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService extends BaseService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonPlanService lessonPlanService;

    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    public List<Question> getQuestionsForQuiz(final Long quizId) {
        return quizQuestionRepository
                .findAllByQuizId(quizId)
                .orElse(new ArrayList<>())
                .stream()
                .map(q -> questionService.getQuestion(q.getQuestionId()))
                .collect(Collectors.toList());
    }

    public Quiz createOrUpdate(final Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void addQuizQuestion(final Long quizId, final Long questionId) {
        final Optional<List<QuizQuestion>> quizQuestionsOpt =
                quizQuestionRepository.findAllByQuizIdAndQuestionId(quizId, questionId);
        if (quizQuestionsOpt.isPresent()) {
            return;
        }
        final Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isPresent()) {
            final Question question = questionService.getQuestion(questionId);
            if (question != null) {
                final QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.setQuizId(quizId);
                quizQuestion.setQuestionId(questionId);
                quizQuestionRepository.save(quizQuestion);
            }
        }
    }

    public void removeQuizQuestion(final Long quizId, final Long questionId) {
        quizQuestionRepository
                .findAllByQuizIdAndQuestionId(quizId, questionId)
                .ifPresent(quizQuestions -> quizQuestionRepository.deleteAll(quizQuestions));
    }

    public Quiz getQuiz(final Long quizId) {
        final Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            final Optional<List<QuizQuestion>> quizQuestionListOpt = quizQuestionRepository.findAllByQuizId(quizId);
            if (quizQuestionListOpt.isPresent()) {
                final List<QuizQuestion> quizQuestionList = quizQuestionListOpt.get();
                final List<Question> questions = new ArrayList<>();
                for (final QuizQuestion quizQuestion : quizQuestionList) {
                    questions.add(questionService.getQuestion(quizQuestion.getQuestionId()));
                }
                quiz.setQuestions(questions);
            }
            quiz.setLessonPlan(lessonPlanService.getLessonPlan(quiz.getLessonPlanId()));
        }
        return quiz;
    }

}
