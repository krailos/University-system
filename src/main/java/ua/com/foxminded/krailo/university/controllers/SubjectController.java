package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectService subjectService;

    private TeacherService teacherService;

    public SubjectController(SubjectService subjectService, TeacherService teacherService) {
	this.subjectService = subjectService;
	this.teacherService = teacherService;
    }

    @GetMapping()
    public String getAllSubjects(Model model) {
	List<Subject> subjects = subjectService.getAll();
	model.addAttribute("subjects", subjects);
	return "subjects/all";
    }

    @GetMapping("/{id}")
    public String getViewSubjectGet(@PathVariable int id, Model model) {
	Subject subject = subjectService.getById(id);
	List<Teacher> teachers = teacherService.getBySubjectId(id);
	subject.setTeachers(teachers);
	model.addAttribute("subject", subject);
	return "subjects/subject";
    }

}
