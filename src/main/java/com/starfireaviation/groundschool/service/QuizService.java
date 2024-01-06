package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.entity.Quiz;
import com.starfireaviation.groundschool.model.entity.QuizQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService extends BaseService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonPlanService lessonPlanService;

    public List<Quiz> getAll() {
        final List<Quiz> quizzes = new ArrayList<>();
        return quizzes;
    }

    public List<Question> getQuestionsForQuiz(final Long quizId) {
        final List<Question> questions = new ArrayList<>();
        return questions;
//        return quizQuestionRepository
//                .findAllByQuizId(quizId)
//                .orElse(new ArrayList<>())
//                .stream()
//                .map(q -> questionService.getQuestion(q.getQuestionId()))
//                .collect(Collectors.toList());
    }

    public Quiz createOrUpdate(final Quiz quiz) {
        return quiz;
//        return quizRepository.save(quiz);
    }

    public Quiz submitQuiz(final Quiz quiz) {
        // Do something
        //return quizRepository.save(quiz);
        return null;
    }

    public void addQuizQuestion(final Long quizId, final Long questionId) {
//        final Optional<List<QuizQuestion>> quizQuestionsOpt =
//                quizQuestionRepository.findAllByQuizId(quizId);
//        if (quizQuestionsOpt.isPresent()) {
//            final List<QuizQuestion> quizQuestions = quizQuestionsOpt.get();
//            if (quizQuestions.stream().anyMatch(qq -> Objects.equals(qq.getQuestionId(), questionId))) {
//                log.info("Question ID: {} is already assigned to Quiz ID: {}", questionId, quizId);
//                return;
//            }
//        }
//        final Optional<Quiz> quizOpt = quizRepository.findById(quizId);
//        if (quizOpt.isPresent()) {
//            final Question question = questionService.getQuestion(questionId);
//            if (question != null) {
//                final QuizQuestion quizQuestion = new QuizQuestion();
//                quizQuestion.setQuizId(quizId);
//                quizQuestion.setQuestionId(questionId);
//                log.info("Assigning Question ID: {} to Quiz ID: {}", questionId, quizId);
//                quizQuestionRepository.save(quizQuestion);
//            } else {
//                log.warn("No question found for ID: {}", questionId);
//            }
//        } else {
//            log.warn("No quiz found for ID: {}", quizId);
//        }
    }

    public void removeQuiz(final Long quizId) {
//        quizQuestionRepository
//                .findAllByQuizId(quizId)
//                .ifPresent(quizQuestions -> quizQuestionRepository.deleteAll(quizQuestions));
//        quizRepository
//                .findById(quizId)
//                .ifPresent(quiz -> quizRepository.delete(quiz));
    }

    public void removeQuizQuestion(final Long quizId, final Long questionId) {
//        quizQuestionRepository
//                .findAllByQuizIdAndQuestionId(quizId, questionId)
//                .ifPresent(quizQuestions -> quizQuestionRepository.deleteAll(quizQuestions));
    }

    public Quiz getQuiz(final Long quizId) {
        return new Quiz();
//        final Quiz quiz = quizRepository.findById(quizId).orElse(null);
//        if (quiz != null) {
//            log.info("Quiz ID: {} found.  Loading questions...", quizId);
//            final Optional<List<QuizQuestion>> quizQuestionListOpt = quizQuestionRepository.findAllByQuizId(quizId);
//            if (quizQuestionListOpt.isPresent()) {
//                final List<QuizQuestion> quizQuestionList = quizQuestionListOpt.get();
//                final List<Question> questions = new ArrayList<>();
//                for (final QuizQuestion quizQuestion : quizQuestionList) {
//                    log.info("Adding Question ID: {} to Quiz ID: {}", quizQuestion.getQuestionId(), quizId);
//                    questions.add(questionService.getQuestion(quizQuestion.getQuestionId()));
//                }
//                quiz.setQuestions(questions);
//            }
//            log.info("Adding Lesson Plan ID: {} to Quiz ID: {}", quiz.getLessonPlanId(), quizId);
//            quiz.setLessonPlan(lessonPlanService.getLessonPlan(quiz.getLessonPlanId()));
//        }
//        return quiz;
    }

}
