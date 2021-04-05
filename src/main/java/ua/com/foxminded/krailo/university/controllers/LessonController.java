package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.service.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
	this.lessonService = lessonService;
    }


    @GetMapping()
    public String getAllLessons(Model model) {
	List<Lesson> lessons = lessonService.getAll();
	model.addAttribute("lessons", lessons);
	return "lessons/all";
    }

    @GetMapping("/{id}")
    public String getViewLesson(@PathVariable int id, Model model) {
	Lesson lesson = lessonService.getById(id);
	model.addAttribute("lesson", lesson);
	return "lessons/lesson";
    }

}
