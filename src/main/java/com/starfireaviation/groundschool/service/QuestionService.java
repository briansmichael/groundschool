package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class QuestionService extends BaseService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Async
    public void update() {
        initCipher(applicationProperties.getSecretKey(), applicationProperties.getInitVector());
        final String query = "SELECT QuestionID, QuestionText, ChapterID, SMCID, SourceID, LastMod, Explanation, "
                + "OldQID, LSCID FROM Questions";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
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
                    questionRepository.save(question);
                }
            } catch (SQLException | InvalidCipherTextException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public List<Question> getQuestionsForChapter(final Long chapterId) {
        return questionRepository.findAllByChapterId(chapterId).orElse(null);
    }

    public Question getQuestion(final Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    private String processImages(final String rawText) {
        final StringBuilder returnText = new StringBuilder(rawText);
        final Pattern pattern = Pattern.compile("\\{\\{IMAGE(.*?)}}");
        final Matcher matcher = pattern.matcher(rawText);
        while (matcher.find()) {
            final String group = matcher.group(1);
            //log.info("Found pattern match: {}", group);
            final String replacementText = returnText.toString().replaceAll("\\{\\{IMAGE"+group+"}}",
                    "<IMG SRC=\"http://media.starfireaviation.com/media/"+group+".png\">");
            returnText.replace(0, returnText.length(), replacementText);
        }
        return returnText.toString();
    }
}
