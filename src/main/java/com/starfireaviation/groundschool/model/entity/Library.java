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

import java.util.Date;

/**
 * Library.
 */
@Data
public class Library {

    /**
     * ID.
     */
    private Long id;

    /**
     * Region.
     */
    private String region;

    /**
     * ParentID.
     */
    private Long parentId;

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    private String description;

    /**
     * IsSection.
     */
    private Long isSection;

    /**
     * Source.
     */
    private String source;

    /**
     * Ordinal.
     */
    private Long ordinal;

    /**
     * Last Modified.
     */
    private Date lastModified;

}
