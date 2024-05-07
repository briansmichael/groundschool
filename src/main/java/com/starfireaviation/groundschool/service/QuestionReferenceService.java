package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionReference;
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
public class QuestionReferenceService extends BaseService {

    private final Map<Long, QuestionReference> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<QuestionReference> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, QuestionID, RefID FROM QuestionsReferences";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionReference questionReference = new QuestionReference();
                    questionReference.setId(rs.getLong(1));
                    questionReference.setQuestionId(rs.getLong(2));
                    questionReference.setRefId(rs.getLong(3));
                    cache.put(questionReference.getId(), questionReference);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
