package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.FigureSection;
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
public class FigureSectionService extends BaseService {

    private final Map<Long, FigureSection> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<FigureSection> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT FigureSectionID, FigureSection, LastMod FROM FigureSections";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final FigureSection figureSection = new FigureSection();
                    figureSection.setFigureSectionId(rs.getLong(1));
                    figureSection.setFigureSection(rs.getString(2));
                    figureSection.setLastModified(rs.getDate(3));
                    cache.put(figureSection.getFigureSectionId(), figureSection);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
