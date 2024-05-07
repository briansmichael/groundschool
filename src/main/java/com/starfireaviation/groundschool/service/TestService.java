package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Test;
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
public class TestService extends BaseService {

    private final Map<Long, Test> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Test> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT TestID, TestName, TestAbbr, GroupID, SortBy, LastMod FROM Tests";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Test test = new Test();
                    test.setTestId(rs.getLong(1));
                    test.setTestName(rs.getString(2));
                    test.setTestAbbr(rs.getString(3));
                    test.setGroupId(rs.getLong(4));
                    test.setSortBy(rs.getLong(5));
                    test.setLastModified(rs.getDate(6));
                    cache.put(test.getTestId(), test);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
