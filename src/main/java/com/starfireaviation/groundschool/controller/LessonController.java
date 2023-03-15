package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.Lesson;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.ChapterService;
import com.starfireaviation.groundschool.service.GroupService;
import com.starfireaviation.groundschool.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
public class LessonController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private LessonService lessonService;

    @GetMapping("/lessons")
    public String getLessons(final Model model) {
        return lessonsView(model);
    }

    @GetMapping("/lessons/{id}")
    public String getLessonPlan(@PathVariable("id") final Long lessonId, final Model model) {
        if (lessonId == null) {
            return lessonsView(model);
        }
        try {
            return lessonView(lessonId, model);
        } catch (Exception e) {
            return lessonsView(model);
        }
    }

    @GetMapping("/lessons/edit/{id}")
    public String editLesson(@PathVariable("id") final Long lessonId, final Model model) {
        log.info("Editing Lesson.");
        model.addAttribute("lesson", lessonService.getLesson(lessonId));
        log.info("Returning lesson id: {}", lessonId);
        return "editlesson"; //view
    }

    @GetMapping("/lessons/new")
    public String newLesson(final Model model) {
        log.info("Creating a new Lesson.");
        model.addAttribute("lesson", new Lesson());
        return "newlesson"; //view
    }

    @PostMapping("/lessons/filter")
    public String filterLessons(@RequestBody MultiValueMap<String, String> formData, final Model model) {
        Long groupId = Long.parseLong(Objects.requireNonNull(formData.getFirst("groupId")));
        Long chapterId = Long.parseLong(Objects.requireNonNull(formData.getFirst("chapterId")));
        final Map<Long, String> groupsMap = groupService.getGroupMap();
        model.addAttribute("groups", groupsMap);
        final Map<Long, String> chaptersMap = chapterService.getChapterMap(groupId);
        model.addAttribute("chapters", chaptersMap);
        if (!chaptersMap.containsKey(chapterId)) {
            chapterId = chaptersMap.keySet().stream().findFirst().orElse(1L);
        }
        final List<Lesson> lessons = lessonService.getLessonsForChapter(chapterId);
        model.addAttribute("lessons", lessons);
        return lessonsView(groupId, chapterId, model);
    }

    @PostMapping("/lessons")
    public String createLesson(final Lesson lesson, final Model model) {
        log.info("Creating Lesson.");
        lessonService.createOrUpdate(lesson);
        return lessonView(lesson.getId(), model);
    }

    @PostMapping("/lessons/update")
    public String updateLessons(final Model model) {
        log.info("Updating Lessons.");
        lessonService.update();
        return "success"; //view
    }

    @PutMapping("/lessons")
    public String updateLesson(final Lesson lesson, final Model model) {
        log.info("Updating Lesson.");
        lessonService.createOrUpdate(lesson);
        return lessonView(lesson.getId(), model);
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") final Long lessonId, final Model model) {
        log.info("Deleting Lesson.");
        lessonService.removeLesson(lessonId);
        return lessonsView(model);
    }

    private String lessonsView(final Model model) {
        return lessonsView(1L, 1L, model);
    }

    private String lessonsView(final Long groupId, final Long chapterId, final Model model) {
        final Map<Long, String> groups = groupService.getGroupMap();
        model.addAttribute("groups", groups);
        final Map<Long, String> chapters = chapterService.getChapterMap(groupId);
        model.addAttribute("chapters", chapters);
        model.addAttribute("chapterId", chapterId);
        final List<Lesson> lessons = lessonService.getLessonsForChapter(chapterId);
        model.addAttribute("lessons", lessons);
        final Selection selection = new Selection();
        selection.setGroupId(groupId);
        selection.setChapterId(chapterId);
        model.addAttribute("selection", selection);

        log.info("Returning lessons");
        return "lessons"; //view
    }

    private String lessonView(final Long lessonId, final Model model) {
        model.addAttribute("lesson", lessonService.getLesson(lessonId));
        log.info("Returning lesson id: {}", lessonId);
        return "lesson"; //view
    }

}
