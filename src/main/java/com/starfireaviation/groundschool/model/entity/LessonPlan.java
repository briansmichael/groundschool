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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * LessonPlan.
 */
@Entity
@Data
@Table(name = "lesson_plan")
public class LessonPlan implements Serializable {

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
     * Created At.
     */
    @Column(name = "created_date")
    private Date createdDate = new Date();

    /**
     * Updated At.
     */
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * Summary.
     */
    @Column(name = "summary")
    private String summary;

    /**
     * Objective.
     */
    @Column(name = "objective")
    private String objective;

    /**
     * Content.
     */
    @Column(name = "content")
    private String content;

    /**
     * Schedule.
     */
    @Column(name = "schedule")
    private String schedule;

    /**
     * Equipment.
     */
    @Column(name = "equipment")
    private String equipment;

    /**
     * Instructor's Actions.
     */
    @Column(name = "instructor_actions")
    private String instructorActions;

    /**
     * Student's Actions.
     */
    @Column(name = "student_actions")
    private String studentActions;

    /**
     * Completion Standards.
     */
    @Column(name = "completion_standards")
    private String completionStandards;

    /**
     * Presentable flag.
     */
    @Column(name = "presentable")
    private boolean presentable;
}
