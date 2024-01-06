package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionRefImage;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionRefImageService extends BaseService {

    private Map<Long, QuestionRefImage> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<QuestionRefImage> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<QuestionRefImage> questionRefImageList = new ArrayList<>();
        final String query = "SELECT ID, QuestionID, ImageID, Annotation FROM QuestionsRefImages";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionRefImage questionRefImage = new QuestionRefImage();
                    questionRefImage.setId(rs.getLong(1));
                    questionRefImage.setQuestionId(rs.getLong(2));
                    questionRefImage.setImageId(rs.getLong(3));
                    questionRefImage.setAnnotation(rs.getString(3));
                    questionRefImageList.add(questionRefImage);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (QuestionRefImage questionRefImage : questionRefImageList) {
            cache.put(questionRefImage.getId(), questionRefImage);
        }
        return questionRefImageList;
    }

    public List<Long> getImageIdsForQuestionId(final Long questionId) {
        return getAll().stream()
                .filter(questionRefImage -> Objects.equals(questionRefImage.getQuestionId(), questionId))
                .map(QuestionRefImage::getImageId)
                .collect(Collectors.toList());
    }
}
