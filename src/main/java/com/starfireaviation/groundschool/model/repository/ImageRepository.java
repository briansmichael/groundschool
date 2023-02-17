package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
