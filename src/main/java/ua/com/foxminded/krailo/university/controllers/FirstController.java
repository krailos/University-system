package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.StudentService;

@Controller
public class FirstController {

    @Autowired
    StudentService studentService;
    
    @GetMapping("/hello")
    public String helloPage() {
	return "hello";
    }
    
    
    @GetMapping("/students")
    public String helloPage(Model model) {
	List<Student> students = studentService.getAll();
	model.addAttribute("students", students);
	return "students";
    }

    
}
