package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
