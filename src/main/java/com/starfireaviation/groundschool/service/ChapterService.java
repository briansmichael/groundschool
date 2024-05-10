package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Chapter;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
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
@DependsOn("questionService")
public class ChapterService extends BaseService {

    private final Map<Long, Chapter> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionService questionService;

    public Chapter getChapter(final Long chapterId) {
        return getAll().stream().filter(chapter -> Objects.equals(chapter.getChapterId(), chapterId)).findFirst().orElse(null);
    }

    public Map<Long, String> getChapterMap(final Long groupId) {
        final Map<Long, String> map = new HashMap<>();
        getAll()
                .stream()
                .filter(chapter -> Objects.equals(groupId, chapter.getGroupId()))
                .peek(c -> log.info("[BEFORE] GroupId: {}; Chapter ID: {}", groupId, c.getChapterId()))
                .filter(chapter -> !questionService.getQuestionsForChapter(chapter.getChapterId()).isEmpty())
                .peek(c -> log.info("[AFTER] GroupId: {}; Chapter ID: {}", groupId, c.getChapterId()))
                .forEach(chapter -> map.put(chapter.getChapterId(), chapter.getChapterName()));
        return map;
    }

    public List<Chapter> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ChapterID, ChapterName, GroupID, SortBy, LastMod FROM Chapters";
        for (final String course: courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Chapter chapter = new Chapter();
                    if ("IOF".equalsIgnoreCase(course)) {
                        chapter.setChapterId(rs.getLong(4));
                    } else {
                        chapter.setChapterId(rs.getLong(1));
                    }
                    chapter.setChapterName(rs.getString(2));
                    chapter.setGroupId(rs.getLong(3));
                    chapter.setSortBy(rs.getLong(4));
                    chapter.setLastModified(rs.getDate(5));
                    if (cache.containsKey(chapter.getChapterId())) {
                        log.warn("{} would have overwritten existing chapter: {}",
                                cache.get(chapter.getChapterId()), chapter);
                    } else {
                        cache.put(chapter.getChapterId(), chapter);
                    }
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
//        cache.values()
//                .stream()
//                .map(Chapter::getChapterId)
//                .sorted()
//                .toList()
//                .forEach(id -> {
//                    final Chapter chapter = cache.get(id);
//                    log.info("Chapter ID: {} Group ID: {} Title: {}",
//                            id, chapter.getGroupId(), chapter.getChapterName());
//                });
    }
}
