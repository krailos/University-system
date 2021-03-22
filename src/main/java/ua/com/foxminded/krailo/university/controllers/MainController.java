package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.service.StudentService;
import ua.com.foxminded.krailo.university.service.SubjectService;

@Controller
public class MainController {

    @Autowired
    StudentService studentService;
    
    @Autowired
    SubjectService subjectService;
    
    
    @GetMapping("/students")
    public String getStudentsPage(Model model) {
	List<Student> students = studentService.getAll();
	model.addAttribute("students", students);
	return "students";
    }
    
    @GetMapping("/subjects")
    public String getSubjectsPage(Model model) {
	List<Subject> subjects = subjectService.getAll();
	model.addAttribute("subjects", subjects);
	return "subjects";
    }
        
    @GetMapping("/index")
    public String getIndexPage() {
	return "index";
    }

    
}
