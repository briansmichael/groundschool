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

@Data
public class Image implements Serializable {

    /**
     * Image ID.
     */
    private Long id;

    /**
     * PicType.
     */
    private Long picType;

    /**
     * GroupID.
     */
    private Long groupId;

    /**
     * Test ID.
     */
    private Long testId;

    /**
     * ImageName.
     */
    private String imageName;

    /**
     * Description.
     */
    private String description;

    /**
     * FileName.
     */
    private String fileName;

    /**
     * Figure Section ID.
     */
    private Long figureSectionId;

    /**
     * Pixels per NM.
     */
    private Double pixelsPerNM;

    /**
     * Sort By.
     */
    private Long sortBy;

    /**
     * Image.
     */
    private byte[] bytes;

    /**
     * Image Library ID.
     */
    private Long imageLibraryId;

    /**
     * Last Modified.
     */
    private Date lastModified;

}
