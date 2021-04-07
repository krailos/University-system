package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
	this.teacherService = teacherService;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
	List<Teacher> teachers = teacherService.getAll();
	model.addAttribute("teachers", teachers);
	return "teachers/all";
    }

    @GetMapping("/{id}")
    public String getTeacher(@PathVariable int id, Model model) {
	Teacher teacher = teacherService.getById(id);
	model.addAttribute("teacher", teacher);
	return "teachers/teacher";
    }

}
