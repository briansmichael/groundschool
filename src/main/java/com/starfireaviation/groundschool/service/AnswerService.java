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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AnswerService extends BaseService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * Choice map.
     */
    private final Map<Long, List<Long>> choices = new HashMap<>();

    public void update() {
        initCipher(applicationProperties.getSecretKey(), applicationProperties.getInitVector());
        final String query = "SELECT AnswerID, AnswerText, QuestionID, IsCorrect, LastMod FROM Answers ORDER BY AnswerID";
        for (final String course : courseService.getCourseList()) {
            choices.clear();
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
                    answer.setChoice(deriveChoice(answer.getQuestionId(), answer.getAnswerId()));
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
     * Derives answer choice.
     *
     * @param questionId Question ID
     * @param answerId Answer ID
     * @return choice
     */
    private String deriveChoice(final Long questionId, final Long answerId) {
        final List<Long> answerIds;
        if (choices.containsKey(questionId)) {
            answerIds = choices.get(questionId);
        } else {
            answerIds = new ArrayList<>();
        }
        final String choice = switch (answerIds.size()) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            default -> null;
        };
        answerIds.add(answerId);
        choices.put(questionId, answerIds);
        return choice;
    }
}
