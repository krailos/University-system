package ua.com.foxminded.krailo.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.util.Paging;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
	this.lessonService = lessonService;
    }

    @GetMapping
    public String getAllLessons(@RequestParam(defaultValue = "${page.defaultPageSize:2}", required = false) int pageSize,
	    @RequestParam(defaultValue = "1", required = false) int pageNumber, Model model) {
	Paging paging = new Paging(pageSize, pageNumber, lessonService.getQuantity());
	model.addAttribute("pageQuantity", paging.getPageQuantity());
	model.addAttribute("lessons", lessonService.getByPage(paging));
	return "lessons/all";
    }

    @GetMapping("/{id}")
    public String getLesson(@PathVariable int id, Model model) {
	Lesson lesson = lessonService.getById(id);
	model.addAttribute("lesson", lesson);
	return "lessons/lesson";
    }

}
