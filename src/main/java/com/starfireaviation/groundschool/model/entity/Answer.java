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

/**
 * Answer.
 */
@Entity
@Data
@Table(name = "answer")
public class Answer implements Comparable, Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    @Id
    @Column(name = "answer_id")
    private Long answerId;

    /**
     * Answer Text.
     */
    @Column(name = "text", length = Integer.MAX_VALUE)
    private String text;

    /**
     * Question ID.
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * Is Correct.
     */
    @Column(name = "correct")
    private Boolean correct;

    /**
     * Choice.
     */
    @Column(name = "choice")
    private String choice;

    /**
     * Last Modified.
     */
    @Column(name = "last_modified")
    private Date lastModified;

    @Override
    public int compareTo(final Object other) {
        Answer otherAnswer = null;
        if (other instanceof Answer) {
            otherAnswer = (Answer) other;
        }
        if (otherAnswer == null) {
            return 0;
        }
        return Character.compare(getChoice().charAt(0), otherAnswer.getChoice().charAt(0));
    }
}
