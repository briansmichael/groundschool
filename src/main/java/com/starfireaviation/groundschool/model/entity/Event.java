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
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Event.
 */
@Data
public class Event implements Comparable, Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private Long id;

    /**
     * Title.
     */
    private String title;

    /**
     * Event started?
     */
    private boolean started = false;

    /**
     * LocalDateTime - startTime.
     */
    private LocalDateTime startTime;

    /**
     * Event completed?
     */
    private boolean completed = false;

    /**
     * LocalDateTime - completedTime.
     */
    private LocalDateTime completedTime;

    /**
     * Google calendar URL.
     */
    private String calendarUrl;

    /**
     * Checkin code.
     */
    private String checkinCode;

    /**
     * Checkin code required.
     */
    private boolean checkinCodeRequired;

    /**
     * Private (no public notices, only private ones).
     */
    private boolean privateEvent = false;

    /**
     * EventType.
     */
    private EventType eventType;

    /**
     * LessonPlan ID.
     */
    private Long lessonPlanId;

    /**
     * Event lead (or primary instructor).
     */
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