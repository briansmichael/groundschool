package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.ACS;
import com.starfireaviation.groundschool.model.repository.ACSRepository;
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
public class ACSService extends BaseService {

    @Autowired
    private ACSRepository acsRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ID, GroupID, ParentID, Code, Description, IsCompletedCode, LastMod FROM ACS";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final ACS acs = new ACS();
                    acs.setId(rs.getLong(1));
                    acs.setGroupId(rs.getLong(2));
                    acs.setParentId(rs.getLong(CommonConstants.THREE));
                    acs.setCode(rs.getString(CommonConstants.FOUR));
                    acs.setDescription(rs.getString(CommonConstants.FIVE));
                    acs.setIsCompletedCode(rs.getLong(CommonConstants.SIX));
                    acs.setLastModified(rs.getDate(CommonConstants.SEVEN));
                    acsRepository.save(acs);
                }
            } catch (SQLException sqle) {
                log.error("Error: {}", sqle.getMessage());
            }
        }
    }
}
