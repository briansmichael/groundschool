package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.TextConst;
import com.starfireaviation.groundschool.model.repository.TextConstRepository;
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
public class TextConstService extends BaseService {

    @Autowired
    private TextConstRepository textConstRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ID, ConstName, ConstValue, GroupID, TestID, LastMod FROM TextConst";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final TextConst textConst = new TextConst();
                    textConst.setId(rs.getLong(1));
                    textConst.setConstName(rs.getString(2));
                    textConst.setConstValue(rs.getString(3));
                    textConst.setGroupId(rs.getLong(4));
                    textConst.setTestId(rs.getLong(5));
                    textConst.setLastModified(rs.getDate(6));
                    textConstRepository.save(textConst);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
