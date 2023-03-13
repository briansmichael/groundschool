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

import com.starfireaviation.groundschool.model.enums.QuizType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Generated;

import java.io.Serializable;
import java.util.List;

/**
 * Quiz.
 */
@Entity
@Data
@Table(name = "quiz")
public class Quiz implements Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * LessonPlan ID.
     */
    @Column(name = "lesson_plan_id")
    private Long lessonPlanId;

    /**
     * QuizType.
     */
    @Column(name = "quiz_type")
    private QuizType quizType;

    /**
     * List of Questions.
     */
    @Transient
    private List<Question> questions;

    /**
     * LessonPlan.
     */
    @Transient
    private LessonPlan lessonPlan;
}
