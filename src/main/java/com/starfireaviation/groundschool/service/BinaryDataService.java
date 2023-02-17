package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.BinaryData;
import com.starfireaviation.groundschool.model.repository.BinaryDataRepository;
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
public class BinaryDataService extends BaseService {

    @Autowired
    private BinaryDataRepository binaryDataRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT ID, Category, GroupID, ImageName, Desc, FileName, BinType, BinData, LastMod FROM BinaryData";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
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
                    binaryDataRepository.save(binaryData);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
