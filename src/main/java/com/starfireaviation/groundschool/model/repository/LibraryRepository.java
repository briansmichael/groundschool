package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
