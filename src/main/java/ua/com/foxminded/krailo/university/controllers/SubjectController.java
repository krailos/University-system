package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String getAllSubjects(Model model) {
	List<Subject> subjects = subjectService.getAll();
	model.addAttribute("subjects", subjects);
	return "subjects/all";
    }

    @GetMapping("/{id}")
    public String getSubject(@PathVariable int id, Model model) {
	model.addAttribute("subject", subjectService.getById(id));
	return "subjects/subject";
    }

    @GetMapping("/create")
    public String createSubject(Model model) {
	model.addAttribute("subject", new Subject());
	model.addAttribute("teachers", teacherService.getAll());
	return "subjects/edit";
    }

    @GetMapping("/edit/{id}")
    public String editSubject(@PathVariable int id, Model model) {
	model.addAttribute("subject", subjectService.getById(id));
	model.addAttribute("teachers", teacherService.getAll());
	return "subjects/edit";
    }

    @PostMapping("/save")
    public String saveSubject(@ModelAttribute("subject") Subject subject) {
	for (Teacher teacher : subject.getTeachers()) {
	    teacher = teacherService.getById(teacher.getId());
	}
	if (subject.getId() == 0) {
	    subjectService.create(subject);
	} else {
	    subjectService.update(subject);
	}
	return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable int id) {
	subjectService.delete(subjectService.getById(id));
	return "redirect:/subjects";
    }

}
