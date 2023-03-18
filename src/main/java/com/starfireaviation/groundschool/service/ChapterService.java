package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Chapter;
import com.starfireaviation.groundschool.model.repository.ChapterRepository;
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
public class ChapterService extends BaseService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseService courseService;

    public void update() {
        final String query = "SELECT ChapterID, ChapterName, GroupID, SortBy, LastMod FROM Chapters";
        for (final String course: courseService.getCourseList()) {
            log.info("Updating {} info...", course);
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Chapter chapter = new Chapter();
                    chapter.setChapterId(rs.getLong(1));
                    chapter.setChapterName(rs.getString(2));
                    chapter.setGroupId(rs.getLong(3));
                    chapter.setSortBy(rs.getLong(4));
                    chapter.setLastModified(rs.getDate(5));
                    chapterRepository.save(chapter);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    public Map<Long, String> getFullChapterMap() {
        final Map<Long, String> map = new HashMap<>();
        chapterRepository
                .findAll()
                .forEach(chapter -> map.put(chapter.getChapterId(), chapter.getChapterName()));
        return map;
    }

    public Chapter getChapter(final Long chapterId) {
        return chapterRepository.findById(chapterId).orElse(null);
    }

    public Map<Long, String> getChapterMap(final Long groupId) {
        final Map<Long, String> map = new HashMap<>();
        chapterRepository
                .findAll()
                .stream()
                .filter(chapter -> groupId == chapter.getGroupId())
                .forEach(chapter -> map.put(chapter.getChapterId(), chapter.getChapterName()));
        return map;
    }
}
