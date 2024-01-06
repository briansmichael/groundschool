package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.ACS;
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
public class ACSService extends BaseService {

    private Map<Long, ACS> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<ACS> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<ACS> acsList = new ArrayList<>();
        final String query = "SELECT ID, GroupID, ParentID, Code, Description, IsCompletedCode, LastMod FROM ACS";
        for (final String course : courseService.getCourseList()) {
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
                    acsList.add(acs);
                }
            } catch (SQLException sqle) {
                log.error("Error: {}", sqle.getMessage());
            }
        }
        for (ACS acs : acsList) {
            cache.put(acs.getId(), acs);
        }
        return acsList;
    }
}
