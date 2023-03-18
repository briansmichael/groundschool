package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Ref;
import com.starfireaviation.groundschool.model.repository.RefRepository;
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
public class RefService extends BaseService {

    @Autowired
    private RefRepository refRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT RefID, RefText, LastMod FROM Refs";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Long remoteId = rs.getLong(1);
                    final Ref ref = new Ref();
                    ref.setRefId(remoteId);
                    ref.setRefText(rs.getString(2));
                    ref.setLastModified(rs.getDate(3));
                    refRepository.save(ref);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
