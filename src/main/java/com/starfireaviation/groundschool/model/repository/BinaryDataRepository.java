package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.BinaryData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryDataRepository extends JpaRepository<BinaryData, Long> {
}
