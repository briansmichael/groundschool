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

import com.starfireaviation.groundschool.model.enums.ComponentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Comment.
 */
@Entity
@Data
@Table(name = "comment")
public class Comment implements Serializable {

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
     * Created Date.
     */
    @Column(name = "created_date")
    private Date createdDate = new Date();

    /**
     * Updated Date.
     */
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    /**
     * Text.
     */
    @Column(name = "text")
    private String text;

    /**
     * User ID.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Reference ID.
     */
    @Column(name = "reference_id")
    private Long referenceId;

    /**
     * ComponentType.
     */
    @Column(name = "component_type")
    private ComponentType componentType;

}
