package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.SubjectMatterCode;
import com.starfireaviation.groundschool.model.repository.SubjectMatterCodeRepository;
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
public class SubjectMatterCodeService extends BaseService {

    @Autowired
    private SubjectMatterCodeRepository subjectMatterCodeRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ID, Code, SourceID, Description, LastMod, IsLSC FROM SubjectMatterCodes";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final SubjectMatterCode subjectMatterCode = new SubjectMatterCode();
                    subjectMatterCode.setId(rs.getLong(1));
                    subjectMatterCode.setCode(rs.getString(2));
                    subjectMatterCode.setSourceId(rs.getLong(3));
                    subjectMatterCode.setDescription(rs.getString(4));
                    subjectMatterCode.setLastModified(rs.getDate(5));
                    subjectMatterCode.setIsLSC(rs.getLong(6));
                    subjectMatterCodeRepository.save(subjectMatterCode);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
