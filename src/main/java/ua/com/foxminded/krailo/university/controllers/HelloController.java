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
public class HelloController {

    @Autowired
    StudentService studentService;
    
    @Autowired
    SubjectService subjectService;
    
    @GetMapping("/hello")
    public String helloPage() {
	return "hello";
    }
    
    
    @GetMapping("/students")
    public String studentsPage(Model model) {
	List<Student> students = studentService.getAll();
	model.addAttribute("students", students);
	return "students";
    }
    
    @GetMapping("/subjects")
    public String subjectsPage(Model model) {
	List<Subject> subjects = subjectService.getAll();
	model.addAttribute("subjects", subjects);
	return "subjects";
    }
    
    @GetMapping("/wellcome")
    public String wellcomePage() {
	return "wellcome";
    }

    
}
