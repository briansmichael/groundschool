package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.service.ACSService;
import com.starfireaviation.groundschool.service.AnswerService;
import com.starfireaviation.groundschool.service.BinaryDataService;
import com.starfireaviation.groundschool.service.ChapterService;
import com.starfireaviation.groundschool.service.FigureSectionService;
import com.starfireaviation.groundschool.service.GroupService;
import com.starfireaviation.groundschool.service.ImageService;
import com.starfireaviation.groundschool.service.LessonService;
import com.starfireaviation.groundschool.service.LibraryService;
import com.starfireaviation.groundschool.service.QuestionACSService;
import com.starfireaviation.groundschool.service.QuestionRefImageService;
import com.starfireaviation.groundschool.service.QuestionReferenceService;
import com.starfireaviation.groundschool.service.QuestionService;
import com.starfireaviation.groundschool.service.QuestionTestService;
import com.starfireaviation.groundschool.service.RefService;
import com.starfireaviation.groundschool.service.SourceService;
import com.starfireaviation.groundschool.service.SubjectMatterCodeService;
import com.starfireaviation.groundschool.service.TestService;
import com.starfireaviation.groundschool.service.TextConstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {

    @Autowired
    private ACSService acsService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private BinaryDataService binaryDataService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private FigureSectionService figureSectionService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private QuestionACSService questionACSService;

    @Autowired
    private QuestionReferenceService questionReferenceService;

    @Autowired
    private QuestionRefImageService questionRefImageService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionTestService questionTestService;

    @Autowired
    private RefService refService;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private SubjectMatterCodeService subjectMatterCodeService;

    @Autowired
    private TestService testService;

    @Autowired
    private TextConstService textConstService;

    @PostMapping
    @Scheduled(cron = "0 0 10 1,10,20 * ?")
    public void updateAll() {
        acsService.update();
        answerService.update();
        binaryDataService.update();
        chapterService.update();
        figureSectionService.update();
        groupService.update();
        imageService.update();
        libraryService.update();
        questionACSService.update();
        questionReferenceService.update();
        questionRefImageService.update();
        questionTestService.update();
        refService.update();
        sourceService.update();
        subjectMatterCodeService.update();
        testService.update();
        textConstService.update();
        questionService.update();
        lessonService.update();
    }
}
