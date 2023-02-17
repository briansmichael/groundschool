package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
