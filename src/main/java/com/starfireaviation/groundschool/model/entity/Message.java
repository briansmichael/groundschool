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

import com.starfireaviation.groundschool.model.enums.NotificationEventType;
import com.starfireaviation.groundschool.model.enums.NotificationType;
import com.starfireaviation.groundschool.model.enums.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@Table(name = "message")
public class Message implements Comparable<Message>, Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Priority.
     */
    @Column(name = "priority")
    private Priority priority;

    /**
     * Payload.
     */
    @Column(name = "payload")
    private String payload;

    /**
     * NotificationType.
     */
    @Column(name = "notification_type")
    private NotificationType notificationType;

    /**
     * NotificationEventType.
     */
    @Column(name = "notification_event_type")
    private NotificationEventType notificationEventType;

    /**
     * User ID.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Quiz ID.
     */
    @Column(name = "quiz_id")
    private Long quizId;

    /**
     * Event ID.
     */
    @Column(name = "event_id")
    private Long eventId;

    /**
     * Question ID.
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * Lesson ID.
     */
    @Column(name = "lesson_id")
    private Long lessonId;

    /**
     * Reference Material ID.
     */
    @Column(name = "reference_material_id")
    private Long referenceMaterialId;

    /**
     * Expiration time.
     */
    @Column(name = "expiration_time")
    private Instant expirationTime;

    /**
     * Compares this message's priority to another message's priority.
     *
     * @param other the object to be compared.
     * @return priority order
     */
    @Override
    public int compareTo(final Message other) {
        if (getPriority() != other.getPriority()) {
            if (other.getPriority() == Priority.HIGH) {
                return -1;
            }
            if (other.getPriority() == Priority.LOW) {
                return 1;
            }
        }
        return 0;
    }
}
