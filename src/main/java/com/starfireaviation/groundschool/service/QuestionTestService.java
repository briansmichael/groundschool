package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionTest;
import com.starfireaviation.groundschool.model.repository.QuestionTestRepository;
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
public class QuestionTestService extends BaseService {

    @Autowired
    private QuestionTestRepository questionTestRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT ID, QuestionID, TestID, IsLinked, SortBy, LinkChapter, IsImportant "
                + "FROM QuestionsTests";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionTest questionTest = new QuestionTest();
                    questionTest.setId(rs.getLong(1));
                    questionTest.setQuestionId(rs.getLong(2));
                    questionTest.setTestId(rs.getLong(3));
                    questionTest.setIsLinked(rs.getLong(4));
                    questionTest.setSortBy(rs.getLong(5));
                    questionTest.setLinkChapter(rs.getLong(6));
                    questionTest.setIsImportant(rs.getLong(7));
                    questionTestRepository.save(questionTest);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
