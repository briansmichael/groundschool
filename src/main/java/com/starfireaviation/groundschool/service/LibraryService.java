package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Library;
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

@Service
@Slf4j
public class LibraryService extends BaseService {

    private final Map<Long, Library> cache = new HashMap<>();

    @Autowired
    private CourseService courseService;

    public List<Library> getAll() {
        return new ArrayList<>(cache.values());
    }

    @PostConstruct
    public void loadData() {
        final String query = "SELECT ID, Region, ParentID, Name, Description, IsSection, Source, Ordinal, LastMod "
                + "FROM Library";
        for (final String course : courseService.getCourseList()) {
            try (Connection sqlLiteConn = getSQLLiteConnection(course);
                 PreparedStatement ps = sqlLiteConn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final Library library = new Library();
                    library.setId(rs.getLong(1));
                    library.setRegion(rs.getString(2));
                    library.setParentId(rs.getLong(3));
                    library.setName(rs.getString(4));
                    library.setDescription(rs.getString(5));
                    library.setIsSection(rs.getLong(6));
                    library.setSource(rs.getString(7));
                    library.setOrdinal(rs.getLong(8));
                    library.setLastModified(rs.getDate(9));
                    cache.put(library.getId(), library);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
