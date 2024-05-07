package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Source;
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
public class SourceService extends BaseService {

    private final Map<Long, Source> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Source> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, Author, Title, Abbreviation, LastMod FROM Sources";
        for (final String course : courseService.getCourseList()) {
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
                    cache.put(source.getId(), source);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
