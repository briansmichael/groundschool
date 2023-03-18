package com.starfireaviation.groundschool.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {

    //public static final String COMPLETE_COURSE_LIST = "AMA,AMG,AMP,ATP,CFI,COM,FLE,IFR,IOF,MCI,MIL,PAR,PVT,SPG,SPI"; //RDP
    public static final String COMPLETE_COURSE_LIST = "PVT,SPG,COM";

    public List<String> getCourseList() {
        return new ArrayList<>(Arrays.stream(COMPLETE_COURSE_LIST.split(",")).toList());
    }
}
