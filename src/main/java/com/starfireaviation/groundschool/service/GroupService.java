package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Group;
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
public class GroupService extends BaseService {

    private Map<Long, Group> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Group> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<Group> groups = new ArrayList<>();
        final String query = "SELECT GroupID, GroupName, GroupAbbr, LastMod FROM Groups";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Group group = new Group();
                    group.setGroupId(rs.getLong(1));
                    group.setGroupName(rs.getString(2));
                    group.setGroupAbbr(rs.getString(3));
                    group.setLastModified(rs.getDate(4));
                    groups.add(group);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (Group group : groups) {
            cache.put(group.getGroupId(), group);
        }
        return groups;
    }

    public Map<Long, String> getGroupMap() {
        final Map<Long, String> map = new HashMap<>();
        getAll()
                .forEach(group -> map.put(group.getGroupId(), group.getGroupName()));
        return map;
    }
}
