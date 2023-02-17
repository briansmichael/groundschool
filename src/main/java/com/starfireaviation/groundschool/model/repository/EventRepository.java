package com.starfireaviation.groundschool.model.repository;

import com.starfireaviation.groundschool.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
