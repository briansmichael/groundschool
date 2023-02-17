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

import com.starfireaviation.groundschool.model.enums.NotificationPreference;
import com.starfireaviation.groundschool.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * User.
 */
@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

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
     * Email.
     */
    @Column(name = "email")
    private String email;

    /**
     * Email verified.
     */
    @Column(name = "email_verified")
    private boolean emailVerified;

    /**
     * Email enabled.
     */
    @Column(name = "email_enabled")
    private boolean emailEnabled;

    /**
     * SMS.
     */
    @Column(name = "sms")
    private String sms;

    /**
     * SMS verified.
     */
    @Column(name = "sms_verified")
    private boolean smsVerified;

    /**
     * SMS enabled.
     */
    @Column(name = "sms_enabled")
    private boolean smsEnabled;

    /**
     * Slack.
     */
    @Column(name = "slack")
    private String slack;

    /**
     * Slack verified.
     */
    @Column(name = "slack_verified")
    private boolean slackVerified;

    /**
     * Slack enabled.
     */
    @Column(name = "slack_enabled")
    private boolean slackEnabled;

    /**
     * Username.
     */
    @Column(name = "username")
    private String username;

    /**
     * Password.
     */
    @Column(name = "password")
    private String password;

    /**
     * First name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Certificate Number.
     */
    @Column(name = "certificate_number")
    private String certificateNumber;

    /**
     * Code for verification purposes.
     */
    @Column(name = "code")
    private String code;

    /**
     * Role.
     */
    @Column(name = "role")
    private Role role;

    /**
     * QuestionPreference.
     */
    @Column(name = "notification_preference")
    private NotificationPreference notificationPreference = NotificationPreference.WEB;

    /**
     * User's Age.
     */
    @Column(name = "age")
    private Integer age;

}
