package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.Image;
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
import java.util.Objects;

@Service
@Slf4j
public class ImageService extends BaseService {

    private final Map<Long, Image> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    public List<Image> getAll() {
        return new ArrayList<>(cache.values());
    }

    public String getImageNameForId(final Long imageId) {
        return getAll().stream().filter(image -> Objects.equals(image.getId(), imageId)).findFirst().map(Image::getImageName).orElse(null);
    }

    public byte[] getImage(final Long imageId) {
        return getAll().stream().filter(image -> Objects.equals(image.getId(), imageId)).map(Image::getBytes).findFirst().orElse(null);
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, PicType, GroupID, TestID, ImageName, Desc, FileName, BinImage, LastMod, " +
                "FigureSectionID, PixelsPerNM, SortBy, ImageLibraryID FROM Images";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Image image = new Image();
                    image.setId(rs.getLong(1));
                    image.setPicType(rs.getLong(2));
                    image.setGroupId(rs.getLong(CommonConstants.THREE));
                    image.setTestId(rs.getLong(CommonConstants.FOUR));
                    image.setImageName(rs.getString(CommonConstants.FIVE));
                    image.setDescription(rs.getString(CommonConstants.SIX));
                    image.setFileName(rs.getString(CommonConstants.SEVEN));
                    if (image.getFileName() != null) {
                        image.setFileName(image.getFileName().replaceAll(" ", "_"));
                    }
                    image.setLastModified(rs.getDate(CommonConstants.NINE));
                    image.setFigureSectionId(rs.getLong(CommonConstants.TEN));
                    image.setPixelsPerNM(rs.getDouble(CommonConstants.ELEVEN));
                    image.setSortBy(rs.getLong(CommonConstants.TWELVE));
                    image.setImageLibraryId(rs.getLong(CommonConstants.THIRTEEN));
                    if (image.getFileName() != null && !"".equals(image.getFileName())) {
                        image.setBytes(rs.getBytes(CommonConstants.EIGHT));
                    }
                    cache.put(image.getId(), image);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
