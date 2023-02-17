package com.starfireaviation.groundschool.service;

import com.starfireaviation.groundschool.model.entity.Library;
import com.starfireaviation.groundschool.model.repository.LibraryRepository;
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
public class LibraryService extends BaseService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private CourseService courseService;

    @Async
    public void update() {
        final String query = "SELECT ID, Region, ParentID, Name, Description, IsSection, Source, Ordinal, LastMod "
                + "FROM Library";
        for (final String course : courseService.getCourseList()) {
            log.info("Updating {} info...", course);
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
                    libraryRepository.save(library);
                }
            } catch (SQLException e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }
}
