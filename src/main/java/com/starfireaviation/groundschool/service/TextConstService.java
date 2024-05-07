package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.TextConst;
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
public class TextConstService extends BaseService {

    private final Map<Long, TextConst> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<TextConst> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, ConstName, ConstValue, GroupID, TestID, LastMod FROM TextConst";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final TextConst textConst = new TextConst();
                    textConst.setId(rs.getLong(1));
                    textConst.setConstName(rs.getString(2));
                    textConst.setConstValue(rs.getString(3));
                    textConst.setGroupId(rs.getLong(4));
                    textConst.setTestId(rs.getLong(5));
                    textConst.setLastModified(rs.getDate(6));
                    cache.put(textConst.getId(), textConst);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
