package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
