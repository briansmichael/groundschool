package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionReference;
import com.starfireaviation.groundschool.model.repository.QuestionReferenceRepository;
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
public class QuestionReferenceService extends BaseService {

    @Autowired
    private QuestionReferenceRepository questionReferenceRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT ID, QuestionID, RefID FROM QuestionsReferences";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionReference questionReference = new QuestionReference();
                    questionReference.setId(rs.getLong(1));
                    questionReference.setQuestionId(rs.getLong(2));
                    questionReference.setRefId(rs.getLong(3));
                    questionReferenceRepository.save(questionReference);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
