package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    private Map<Long, Test> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Test> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<Test> tests = new ArrayList<>();
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
                    tests.add(test);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (Test test : tests) {
            cache.put(test.getTestId(), test);
        }
        return tests;
    }
}
