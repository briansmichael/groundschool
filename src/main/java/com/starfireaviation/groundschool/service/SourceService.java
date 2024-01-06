package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Source;
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
public class SourceService extends BaseService {

    private Map<Long, Source> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Source> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<Source> sources = new ArrayList<>();
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
                    sources.add(source);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (Source source : sources) {
            cache.put(source.getId(), source);
        }
        return sources;
    }
}
