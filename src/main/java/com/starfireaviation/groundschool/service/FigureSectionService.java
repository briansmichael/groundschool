package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.FigureSection;
import com.starfireaviation.groundschool.model.repository.FigureSectionRepository;
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
public class FigureSectionService extends BaseService {

    @Autowired
    private FigureSectionRepository figureSectionRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
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
                    figureSectionRepository.save(figureSection);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
