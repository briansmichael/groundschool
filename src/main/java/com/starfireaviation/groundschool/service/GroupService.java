package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Group;
import com.starfireaviation.groundschool.model.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GroupService extends BaseService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT GroupID, GroupName, GroupAbbr, LastMod FROM Groups";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Group group = new Group();
                    group.setGroupId(rs.getLong(1));
                    group.setGroupName(rs.getString(2));
                    group.setGroupAbbr(rs.getString(3));
                    group.setLastModified(rs.getDate(4));
                    groupRepository.save(group);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public Map<Long, String> getGroupMap() {
        final Map<Long, String> map = new HashMap<>();
        groupRepository
                .findAll()
                .forEach(group -> map.put(group.getGroupId(), group.getGroupName()));
        return map;
    }
}
