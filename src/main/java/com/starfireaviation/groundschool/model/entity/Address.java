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

/**
 * Address.
 */
@Entity
@Data
@Table(name = "address")
public class Address implements Serializable {

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
     * AddressLine1.
     */
    @Column(name = "address_line_1")
    private String addressLine1;

    /**
     * AddressLine2.
     */
    @Column(name = "address_line_2")
    private String addressLine2;

    /**
     * City.
     */
    @Column(name = "city")
    private String city;

    /**
     * State.
     */
    @Column(name = "state")
    private String state;

    /**
     * ZipCode.
     */
    @Column(name = "zip_code")
    private String zipCode;

}
