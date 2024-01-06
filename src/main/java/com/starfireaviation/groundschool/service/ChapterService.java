package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Chapter;
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
public class ChapterService extends BaseService {

    private Map<Long, Chapter> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public Map<Long, String> getFullChapterMap() {
        final Map<Long, String> map = new HashMap<>();
        getAll()
                .forEach(chapter -> map.put(chapter.getChapterId(), chapter.getChapterName()));
        return map;
    }

    public Chapter getChapter(final Long chapterId) {
        return getAll().stream().filter(chapter -> Objects.equals(chapter.getChapterId(), chapterId)).findFirst().orElse(null);
    }

    public Map<Long, String> getChapterMap(final Long groupId) {
        final Map<Long, String> map = new HashMap<>();
        getAll()
                .stream()
                .filter(chapter -> Objects.equals(groupId, chapter.getGroupId()))
                .forEach(chapter -> map.put(chapter.getChapterId(), chapter.getChapterName()));
        return map;
    }

    public List<Long> getAllChapterIDs() {
        return getAll()
                .stream()
                .map(Chapter::getChapterId)
                .collect(Collectors.toList());
    }

    public List<Chapter> getAll() {
        if (!CollectionUtils.isEmpty(cache)) {
            return new ArrayList<>(cache.values());
        }
        final List<Chapter> chapters = new ArrayList<>();
        final String query = "SELECT ChapterID, ChapterName, GroupID, SortBy, LastMod FROM Chapters";
        for (final String course: courseService.getCourseList()) {
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
                    chapters.add(chapter);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
        for (Chapter chapter : chapters) {
            cache.put(chapter.getChapterId(), chapter);
        }
        return chapters;
    }
}
