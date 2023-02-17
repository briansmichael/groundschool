package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
