package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
