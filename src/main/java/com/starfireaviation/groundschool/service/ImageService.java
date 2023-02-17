package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.constants.CommonConstants;
import com.starfireaviation.groundschool.model.entity.Image;
import com.starfireaviation.groundschool.model.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Slf4j
public class ImageService extends BaseService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Async
    public void update() {
        final String query = "SELECT ID, PicType, GroupID, TestID, ImageName, Desc, FileName, BinImage, LastMod, " +
                "FigureSectionID, PixelsPerNM, SortBy, ImageLibraryID FROM Images";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
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
                        //final String fileName = IMAGE_DIR + "/" + image.getFileName();
                        final String fileName = applicationProperties.getImagesDir() + image.getId() + ".png";
                        FileUtils.writeByteArrayToFile(new File(fileName), rs.getBytes(CommonConstants.EIGHT));
                    }
                    imageRepository.save(image);
                }
            } catch (SQLException | IOException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public String getImageNameForId(final Long imageId) {
        return imageRepository.findById(imageId).map(Image::getImageName).orElse(null);
    }
}
