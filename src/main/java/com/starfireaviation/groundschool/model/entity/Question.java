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

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Question.
 */
@Data
public class Question implements Serializable {

    /**
     * Question ID.
     */
    private Long questionId;

    /**
     * Old Question ID.
     */
    private Long oldQuestionId;

    /**
     * Question Text.
     */
    private String text;

    /**
     * Chapter ID.
     */
    private Long chapterId;

    /**
     * SMC ID.
     */
    private Long smcId;

    /**
     * Source.
     */
    private String source;

    /**
     * Last Modified.
     */
    private Date lastModified;

    /**
     * Explanation.
     */
    private String explanation;

    /**
     * LearningStatementCode.
     */
    private String learningStatementCode;

    /**
     * Learning Statement Code ID.
     */
    private Long lscId;

    /**
     * List of answers.
     */
    private List<Answer> answers;

    /**
     * List of image IDs.
     */
    private List<Long> imageIds;

}
