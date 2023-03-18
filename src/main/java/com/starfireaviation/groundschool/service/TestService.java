package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Test;
import com.starfireaviation.groundschool.model.repository.TestRepository;
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
public class TestService extends BaseService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT TestID, TestName, TestAbbr, GroupID, SortBy, LastMod FROM Tests";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
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
                    testRepository.save(test);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
