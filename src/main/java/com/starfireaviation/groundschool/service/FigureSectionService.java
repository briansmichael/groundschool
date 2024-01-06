package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.FigureSection;
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
public class FigureSectionService extends BaseService {

    private Map<Long, FigureSection> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<FigureSection> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<FigureSection> figureSectionList = new ArrayList<>();
        final String query = "SELECT FigureSectionID, FigureSection, LastMod FROM FigureSections";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final FigureSection figureSection = new FigureSection();
                    figureSection.setFigureSectionId(rs.getLong(1));
                    figureSection.setFigureSection(rs.getString(2));
                    figureSection.setLastModified(rs.getDate(3));
                    figureSectionList.add(figureSection);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (FigureSection figureSection : figureSectionList) {
            cache.put(figureSection.getFigureSectionId(), figureSection);
        }
        return figureSectionList;
    }
}
