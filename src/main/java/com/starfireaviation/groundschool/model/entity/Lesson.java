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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Lesson.
 */
@Entity
@Data
@Table(name = "lesson")
public class Lesson implements Serializable {

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
     * Group.
     */
    @Column(name = "grp")
    private Long groupId;

    /**
     * Chapter.
     */
    @Column(name = "chapter")
    private Long chapterId;

    /**
     * ReferenceId.
     */
    @Column(name = "reference_id")
    private Long referenceId;

    /**
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * Text.
     */
    @Column(name = "text", length = Integer.MAX_VALUE)
    private String text;

    /**
     * Required for course.
     */
    @Column(name = "required")
    private boolean required;

}
