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

/**
 * Answer.
 */
@Data
public class Answer implements Comparable, Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private Long answerId;

    /**
     * Answer Text.
     */
    private String text;

    /**
     * Question ID.
     */
    private Long questionId;

    /**
     * Is Correct.
     */
    private Boolean correct;

    /**
     * Choice.
     */
    private String choice;

    /**
     * Last Modified.
     */
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
