package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.SubjectMatterCode;
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
public class SubjectMatterCodeService extends BaseService {

    private Map<Long, SubjectMatterCode> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<SubjectMatterCode> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<SubjectMatterCode> subjectMatterCodeList = new ArrayList<>();
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
                    subjectMatterCodeList.add(subjectMatterCode);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (SubjectMatterCode subjectMatterCode : subjectMatterCodeList) {
            cache.put(subjectMatterCode.getId(), subjectMatterCode);
        }
        return subjectMatterCodeList;
    }
}
