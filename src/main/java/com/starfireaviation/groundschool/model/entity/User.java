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
import lombok.Data;

import java.io.Serializable;

/**
 * User.
 */
@Data
public class User implements Serializable {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private Long id;

    /**
     * Email.
     */
    private String email;

    /**
     * Email verified.
     */
    private boolean emailVerified;

    /**
     * Email enabled.
     */
    private boolean emailEnabled;

    /**
     * SMS.
     */
    private String sms;

    /**
     * SMS verified.
     */
    private boolean smsVerified;

    /**
     * SMS enabled.
     */
    private boolean smsEnabled;

    /**
     * Slack.
     */
    private String slack;

    /**
     * Slack verified.
     */
    private boolean slackVerified;

    /**
     * Slack enabled.
     */
    private boolean slackEnabled;

    /**
     * Username.
     */
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    /**
     * Certificate Number.
     */
    private String certificateNumber;

    /**
     * Code for verification purposes.
     */
    private String code;

    /**
     * Role.
     */
    private Role role;

    /**
     * QuestionPreference.
     */
    private NotificationPreference notificationPreference = NotificationPreference.WEB;

    /**
     * User's Age.
     */
    private Integer age;

}
