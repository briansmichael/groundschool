package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.BinaryData;
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
public class BinaryDataService extends BaseService {

    private Map<Long, BinaryData> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<BinaryData> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<BinaryData> binaryDataList = new ArrayList<>();
        final String query = "SELECT ID, Category, GroupID, ImageName, Desc, FileName, BinType, BinData, LastMod FROM BinaryData";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final BinaryData binaryData = new BinaryData();
                    binaryData.setId(rs.getLong(1));
                    binaryData.setCategory(rs.getLong(2));
                    binaryData.setGroupId(rs.getLong(3));
                    binaryData.setImageName(rs.getString(4));
                    binaryData.setDescription(rs.getString(5));
                    binaryData.setFileName(rs.getString(6));
                    binaryData.setBinType(rs.getLong(7));
                    binaryData.setLastModified(rs.getDate(9));
                    binaryDataList.add(binaryData);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (BinaryData binaryData : binaryDataList) {
            cache.put(binaryData.getId(), binaryData);
        }
        return binaryDataList;
    }
}
