package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.Question;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@DependsOn("answerService")
public class QuestionService extends BaseService {

    private final Map<Long, Question> cache = new HashMap<>();

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionRefImageService questionRefImageService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    public List<Question> getAll() {
        return new ArrayList<>(cache.values());
    }

    public List<Question> getQuestionsForChapter(final Long chapterId) {
        return getAll().stream().filter(question -> Objects.equals(question.getChapterId(), chapterId)).collect(Collectors.toList());
    }

    public Question getQuestion(final Long questionId) {
        final Question question = getAll().stream().filter(q -> Objects.equals(q.getQuestionId(), questionId)).findFirst().orElse(null);
        if (question != null) {
            question.setAnswers(answerService
                    .getAnswersForQuestion(questionId)
                    .stream()
                    .sorted()
                    .collect(Collectors.toList()));
            question.setImageIds(questionRefImageService.getImageIdsForQuestionId(questionId));
        }
        return question;
    }

    private String processImages(final String rawText) {
        final StringBuilder returnText = new StringBuilder(rawText);
        final Pattern pattern = Pattern.compile("\\{\\{IMAGE(.*?)}}");
        final Matcher matcher = pattern.matcher(rawText);
        while (matcher.find()) {
            final String group = matcher.group(1);
            //log.info("Found pattern match: {}", group);
            final String replacementText = returnText.toString().replaceAll("\\{\\{IMAGE"+group+"}}",
                    "<IMG SRC=\"/images/"+group+"\">");
            returnText.replace(0, returnText.length(), replacementText);
        }
        return returnText.toString();
    }

    @PostConstruct
    public void loadData() {
        initCipher(applicationProperties.getSecretKey(), applicationProperties.getInitVector());
        final String query = "SELECT QuestionID, QuestionText, ChapterID, SMCID, SourceID, LastMod, Explanation, "
                + "OldQID, LSCID FROM Questions";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Question question = new Question();
                    question.setQuestionId(rs.getLong(1));
                    question.setText(decrypt(rs.getString(2)));
                    question.setChapterId(rs.getLong(CommonConstants.THREE));
                    question.setSmcId(rs.getLong(CommonConstants.FOUR));
                    question.setSource(rs.getString(CommonConstants.FIVE));
                    question.setLastModified(rs.getDate(CommonConstants.SIX));
                    //question.setExplanation(Jsoup.parse(decrypt(rs.getString(CommonConstants.SEVEN))).text());
                    question.setExplanation(processImages(decrypt(rs.getString(CommonConstants.SEVEN))));
                    question.setOldQuestionId(rs.getLong(CommonConstants.EIGHT));
                    question.setLscId(rs.getLong(CommonConstants.NINE));
                    cache.put(question.getQuestionId(), question);
                }
            } catch (SQLException | InvalidCipherTextException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
