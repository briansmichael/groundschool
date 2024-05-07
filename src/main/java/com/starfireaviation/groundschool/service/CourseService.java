package com.starfireaviation.groundschool.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {

    public static final String COMPLETE_COURSE_LIST = "AMA,AMG,AMP,CFI,COM,FLE,IFR,MCI,MIL,PAR,PVT,SPG,SPI"; //IOF,PEX,RDP,ATP,IAU,SPW,CTO
    //public static final String COMPLETE_COURSE_LIST = "IOF";

    public List<String> getCourseList() {
        return new ArrayList<>(Arrays.stream(COMPLETE_COURSE_LIST.split(",")).toList());
    }
}
