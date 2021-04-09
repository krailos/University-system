package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.service.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
	this.lessonService = lessonService;
    }

    @GetMapping
    public String getAllLessons(@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
	    @RequestParam(value = "pageId", defaultValue = "1", required = false) int pageId, Model model) {
	int audienceQuantity = lessonService.getQuantity();
	int pageQuantity = audienceQuantity % pageSize == 0 ? audienceQuantity / pageSize
		: audienceQuantity / pageSize + 1;
	List<Lesson> lessonsByLimit = lessonService.getByPage(pageSize, pageSize * (pageId - 1));
	model.addAttribute("pageQuantity", pageQuantity);
	model.addAttribute("lessons", lessonsByLimit);
	return "lessons/all";
    }

    @GetMapping("/{id}")
    public String getLesson(@PathVariable int id, Model model) {
	Lesson lesson = lessonService.getById(id);
	model.addAttribute("lesson", lesson);
	return "lessons/lesson";
    }

}
