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

import com.starfireaviation.groundschool.model.enums.ActivityType;
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
 * Activity.
 */
@Entity
@Data
@Table(name = "activity")
public class Activity implements Serializable {

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
     * Title.
     */
    @Column(name = "title")
    private String title;

    /**
     * Duration (in seconds).
     */
    @Column(name = "duration")
    private long duration;

    /**
     * ActivityType.
     */
    @Column(name = "activity_type")
    private ActivityType activityType;

    /**
     * Reference ID (I.E. quiz ID).
     */
    @Column(name = "reference_id")
    private Long referenceId;

}
