package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Ref;
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
public class RefService extends BaseService {

    private Map<Long, Ref> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Ref> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<Ref> refs = new ArrayList<>();
        final String query = "SELECT RefID, RefText, LastMod FROM Refs";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Long remoteId = rs.getLong(1);
                    final Ref ref = new Ref();
                    ref.setRefId(remoteId);
                    ref.setRefText(rs.getString(2));
                    ref.setLastModified(rs.getDate(3));
                    refs.add(ref);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (Ref ref : refs) {
            cache.put(ref.getRefId(), ref);
        }
        return refs;
    }
}
