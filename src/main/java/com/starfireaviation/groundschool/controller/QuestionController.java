package com.starfireaviation.groundschool.controller;

import com.starfireaviation.groundschool.model.entity.Chapter;
import com.starfireaviation.groundschool.model.entity.Question;
import com.starfireaviation.groundschool.model.web.Selection;
import com.starfireaviation.groundschool.service.ChapterService;
import com.starfireaviation.groundschool.service.GroupService;
import com.starfireaviation.groundschool.service.QuestionService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@Slf4j
public class QuestionController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public String getQuestion(@PathParam("questionId") final Long questionId, final Model model) {
        try {
            final Question question = questionService.getQuestion(questionId);
            final Long chapterId = question.getChapterId();
            final Chapter chapter = chapterService.getChapter(chapterId);
            final Long groupId = chapter.getGroupId();
            final Map<Long, String> groups = groupService.getGroupMap();
            model.addAttribute("groups", groups);
            model.addAttribute("groupId", groupId);
            model.addAttribute("chapters", chapterService.getChapterMap(groupId));
            model.addAttribute("chapterId", chapterId);
            final List<Question> questions = questionService.getQuestionsForChapter(chapterId);
            model.addAttribute("questions", questions);
            return questionView(groupId, chapterId, questionId, model);
        } catch (Exception e) {
            return defaultView(model);
        }
    }

    @PostMapping("/questions")
    public String questions(@RequestBody MultiValueMap<String, String> formData, final Model model) {
        Long groupId = Long.parseLong(Objects.requireNonNull(formData.getFirst("groupId")));
        Long chapterId = Long.parseLong(Objects.requireNonNull(formData.getFirst("chapterId")));
        Long questionId = Long.parseLong(Objects.requireNonNull(formData.getFirst("questionId")));
        final Map<Long, String> groupsMap = groupService.getGroupMap();
        model.addAttribute("groups", groupsMap);
        final Map<Long, String> chaptersMap = chapterService.getChapterMap(groupId);
        model.addAttribute("chapters", chaptersMap);
        if (!chaptersMap.containsKey(chapterId)) {
            chapterId = chaptersMap.keySet().stream().findFirst().orElse(1L);
        }
        final List<Question> questions = questionService.getQuestionsForChapter(chapterId);
        model.addAttribute("questions", questions);
        final Long finalQuestionId = questionId;
        if (questions.stream().noneMatch(q -> Objects.equals(q.getQuestionId(), finalQuestionId))) {
            questionId = questions.stream().map(Question::getQuestionId).findFirst().orElse(1L);
        }
        return questionView(groupId, chapterId, questionId, model);
    }

    private String defaultView(final Model model) {
        final Long groupId = 1L;
        final Map<Long, String> groups = groupService.getGroupMap();
        model.addAttribute("groups", groups);
        final Map<Long, String> chapters = chapterService.getChapterMap(groupId);
        model.addAttribute("chapters", chapters);
        final Long chapterId = chapters.keySet().stream().findFirst().orElse(1L);
        model.addAttribute("chapterId", chapterId);
        final List<Question> questions = questionService.getQuestionsForChapter(chapterId);
        model.addAttribute("questions", questions);
        final Long questionId = questions.get(0).getQuestionId(); //5692L;
        return questionView(groupId, chapterId, questionId, model);
    }

    private String questionView(final Long groupId, final Long chapterId, final Long questionId, final Model model) {
        model.addAttribute("question", questionService.getQuestion(questionId));

        final Selection selection = new Selection();
        selection.setGroupId(groupId);
        selection.setChapterId(chapterId);
        selection.setQuestionId(questionId);
        model.addAttribute("selection", selection);
        return "question"; //view
    }

}
