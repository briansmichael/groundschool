package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionTest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class QuestionTestService extends BaseService {

    private final Map<Long, QuestionTest> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<QuestionTest> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, QuestionID, TestID, IsLinked, SortBy, LinkChapter, IsImportant "
                + "FROM QuestionsTests";
        for (final String course : courseService.getCourseList()) {
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
                    cache.put(questionTest.getId(), questionTest);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
