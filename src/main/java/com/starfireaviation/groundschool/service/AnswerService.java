package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.Answer;
import com.starfireaviation.groundschool.model.repository.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AnswerService extends BaseService {

    /**
     * Answer Choices List.
     */
    public static final String ANSWER_CHOICES = "A,B,C,D,E,F,G,H";

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Async
    public void update() {
        initCipher(applicationProperties.getSecretKey(), applicationProperties.getInitVector());
        final String query = "SELECT AnswerID, AnswerText, QuestionID, IsCorrect, LastMod FROM Answers";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Answer answer = new Answer();
                    answer.setAnswerId(rs.getLong(1));
                    //answer.setText(Jsoup.parse(decrypt(rs.getString(2))).text());
                    answer.setText(decrypt(rs.getString(2)));
                    answer.setQuestionId(rs.getLong(CommonConstants.THREE));
                    answer.setCorrect(rs.getBoolean(CommonConstants.FOUR));
                    answer.setLastModified(rs.getDate(CommonConstants.FIVE));
                    answer.setChoice(deriveChoice(answer.getChoice(), answer.getQuestionId()));
                    answerRepository.save(answer);
                }
            } catch (SQLException | InvalidCipherTextException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public List<Answer> getAnswersForQuestion(final Long questionId) {
        return answerRepository.findAllByQuestionId(questionId).orElse(new ArrayList<>());
    }

    /**
     * Derives choice for answer, if not already set.
     *
     * @param choice prior value
     * @param questionId question ID
     * @return derived choice value
     */
    private String deriveChoice(final String choice, final Long questionId) {
        if (choice != null) {
            return choice;
        }
        final ArrayList<String> choices = new ArrayList<>(Arrays.asList(ANSWER_CHOICES.split(",")));
        answerRepository.findAllByQuestionId(questionId)
                .ifPresent(answerEntities -> answerEntities
                        .stream()
                        .filter(answer -> answer.getChoice() != null)
                        .forEach(answer -> choices.remove(answer.getChoice())));
        return choices.get(0);
    }

}
