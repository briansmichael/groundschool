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
 * Question.
 */
@Entity
@Data
@Table(name = "question")
public class Question implements Serializable {

    /**
     * Question ID.
     */
    @Id
    @Column(name = "question_id")
    private Long questionId;

    /**
     * Old Question ID.
     */
    @Column(name = "old_question_id")
    private Long oldQuestionId;

    /**
     * Question Text.
     */
    @Column(name = "text", length = Integer.MAX_VALUE)
    private String text;

    /**
     * Chapter ID.
     */
    @Column(name = "chapter_id")
    private Long chapterId;

    /**
     * SMC ID.
     */
    @Column(name = "smc_id")
    private Long smcId;

    /**
     * Source.
     */
    @Column(name = "source")
    private String source;

    /**
     * Last Modified.
     */
    @Column(name = "last_modified")
    private Date lastModified;

    /**
     * Explanation.
     */
    @Column(name = "explanation", length = Integer.MAX_VALUE)
    private String explanation;

    /**
     * LearningStatementCode.
     */
    @Column(name = "learning_statement_code")
    private String learningStatementCode;

    /**
     * Learning Statement Code ID.
     */
    @Column(name = "lsc_id")
    private Long lscId;

}
