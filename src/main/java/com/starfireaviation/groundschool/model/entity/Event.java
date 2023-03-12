/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.groundschool.model.entity;

import com.starfireaviation.groundschool.model.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Event.
 */
@Entity
@Data
@Table(name = "event")
public class Event implements Comparable, Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * Event started?
     */
    @Column(name = "started")
    private boolean started = false;

    /**
     * LocalDateTime - startTime.
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * Event completed?
     */
    @Column(name = "completed")
    private boolean completed = false;

    /**
     * LocalDateTime - completedTime.
     */
    @Column(name = "completed_time")
    private LocalDateTime completedTime;

    /**
     * Google calendar URL.
     */
    @Column(name = "calendar_url")
    private String calendarUrl;

    /**
     * Checkin code.
     */
    @Column(name = "checkin_code")
    private String checkinCode;

    /**
     * Checkin code required.
     */
    @Column(name = "checkin_code_required")
    private boolean checkinCodeRequired;

    /**
     * Private (no public notices, only private ones).
     */
    @Column(name = "private_event")
    private boolean privateEvent = false;

    /**
     * EventType.
     */
    @Column(name = "event_type")
    private EventType eventType;

    /**
     * LessonPlan ID.
     */
    @Column(name = "lesson_plan_id")
    private Long lessonPlanId;

    /**
     * Event lead (or primary instructor).
     */
    @Column(name = "event_lead")
    private Long lead;

    @Override
    public int compareTo(final Object other) {
        Event otherEvent = null;
        if (other instanceof Event) {
            otherEvent = (Event) other;
        }
        if (otherEvent == null) {
            return 0;
        }
        if (getStartTime().isBefore(otherEvent.getStartTime())) {
            return -1;
        }
        return 1;
    }
}