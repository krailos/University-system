package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
	this.subjectService = subjectService;
    }

    @GetMapping
    public String getAllSubjects(Model model) {
	List<Subject> subjects = subjectService.getAll();
	model.addAttribute("subjects", subjects);
	return "subjects/all";
    }

    @GetMapping("/{id}")
    public String getSubject(@PathVariable int id, Model model) {
	Subject subject = subjectService.getById(id);
	model.addAttribute("subject", subject);
	return "subjects/subject";
    }

}
