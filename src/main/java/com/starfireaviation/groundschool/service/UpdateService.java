package com.starfireaviation.groundschool.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateService {

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

    @Scheduled(cron = "0 0 10 2,11,21 * ?")
    @PostConstruct
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
