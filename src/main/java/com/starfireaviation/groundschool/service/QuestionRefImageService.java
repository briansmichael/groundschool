package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.QuestionRefImage;
import com.starfireaviation.groundschool.model.repository.QuestionRefImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionRefImageService extends BaseService {

    @Autowired
    private QuestionRefImageRepository questionRefImageRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ID, QuestionID, ImageID, Annotation FROM QuestionsRefImages";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final QuestionRefImage questionRefImage = new QuestionRefImage();
                    questionRefImage.setId(rs.getLong(1));
                    questionRefImage.setQuestionId(rs.getLong(2));
                    questionRefImage.setImageId(rs.getLong(3));
                    questionRefImage.setAnnotation(rs.getString(3));
                    questionRefImageRepository.save(questionRefImage);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public List<Long> getImageIdsForQuestionId(final Long questionId) {
        return questionRefImageRepository
                .findAllByQuestionId(questionId)
                .orElse(List.of())
                .stream()
                .map(QuestionRefImage::getImageId)
                .collect(Collectors.toList());
    }
}
