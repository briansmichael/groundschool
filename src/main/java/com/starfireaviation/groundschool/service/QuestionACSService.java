package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionACS;
import com.starfireaviation.groundschool.model.repository.QuestionACSRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Slf4j
public class QuestionACSService extends BaseService {

    @Autowired
    private QuestionACSRepository questionACSRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ID, QuestionID, ACSID FROM QuestionsACS";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionACS questionACS = new QuestionACS();
                    questionACS.setId(rs.getLong(1));
                    questionACS.setQuestionId(rs.getLong(2));
                    questionACS.setAcsId(rs.getLong(3));
                    questionACSRepository.save(questionACS);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
