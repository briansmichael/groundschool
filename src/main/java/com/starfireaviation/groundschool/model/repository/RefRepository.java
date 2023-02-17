package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Ref;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefRepository extends JpaRepository<Ref, Long> {
}
