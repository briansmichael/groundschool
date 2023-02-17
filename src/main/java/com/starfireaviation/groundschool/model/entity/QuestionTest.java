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

/**
 * QuestionTest.
 */
@Entity
@Data
@Table(name = "question_test")
public class QuestionTest implements Serializable {

    /**
     * ID.
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Question ID.
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * Test ID.
     */
    @Column(name = "test_id")
    private Long testId;

    /**
     * Is Linked.
     */
    @Column(name = "is_linked")
    private Long isLinked;

    /**
     * Sort By.
     */
    @Column(name = "sort_by")
    private Long sortBy;

    /**
     * Link Chapter.
     */
    @Column(name = "link_chapter")
    private Long linkChapter;

    /**
     * Is Important.
     */
    @Column(name = "is_important")
    private Long isImportant;

}
