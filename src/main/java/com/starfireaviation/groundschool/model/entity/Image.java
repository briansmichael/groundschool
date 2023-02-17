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

@Entity
@Data
@Table(name = "image")
public class Image implements Serializable {

    /**
     * Image ID.
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * PicType.
     */
    @Column(name = "pic_type")
    private Long picType;

    /**
     * GroupID.
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * Test ID.
     */
    @Column(name = "test_id")
    private Long testId;

    /**
     * ImageName.
     */
    @Column(name = "image_name")
    private String imageName;

    /**
     * Description.
     */
    @Column(name = "description")
    private String description;

    /**
     * FileName.
     */
    @Column(name = "filename")
    private String fileName;

    /**
     * Figure Section ID.
     */
    @Column(name = "figure_section_id")
    private Long figureSectionId;

    /**
     * Pixels per NM.
     */
    @Column(name = "pixels_per_nm")
    private Double pixelsPerNM;

    /**
     * Sort By.
     */
    @Column(name = "sort_by")
    private Long sortBy;

    /**
     * Image Library ID.
     */
    @Column(name = "image_library_id")
    private Long imageLibraryId;

    /**
     * Last Modified.
     */
    @Column(name = "last_modified")
    private Date lastModified;

}
