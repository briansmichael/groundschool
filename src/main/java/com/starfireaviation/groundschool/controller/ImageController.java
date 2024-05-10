package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.config.ApplicationProperties;
import com.starfireaviation.groundschool.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@DependsOn("imageService")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/{imageId}")
    public byte[] getImage(@PathVariable("imageId") final Long imageId) {
        return imageService.getImage(imageId);
    }
}
