package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Source;
import com.starfireaviation.groundschool.model.repository.SourceRepository;
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
public class SourceService extends BaseService {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT ID, Author, Title, Abbreviation, LastMod FROM Sources";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Source source = new Source();
                    source.setId(rs.getLong(1));
                    source.setAuthor(rs.getString(2));
                    source.setTitle(rs.getString(3));
                    source.setAbbreviation(rs.getString(4));
                    source.setLastModified(rs.getDate(5));
                    sourceRepository.save(source);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
