package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.SubjectService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    LessonService lessonService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GroupService groupService;

    @GetMapping()
    public String getLessonsStartPage() {
	return "lessons/lessons";
    }

    @GetMapping("/all")
    public String getAllLessons(Model model) {
	List<Lesson> lessons = lessonService.getAll();
	model.addAttribute("lessons", lessons);
	return "lessons/lessonsAll";
    }

    @GetMapping("/findLessonById/{id}")
    public String getViewLesson(@PathVariable("id") int id, Model model) {
	Lesson lesson = lessonService.getById(id);
	model.addAttribute("lesson", lesson);
	return "lessons/lessonView";
    }

}
