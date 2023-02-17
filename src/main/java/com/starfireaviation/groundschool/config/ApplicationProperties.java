package com.starfireaviation.groundschool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("groundschool")
@Data
public class ApplicationProperties {

    private String secretKey;

    private String initVector;

    private String dbSrcLocation;

    private String imagesDir;
}
