package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
	this.studentService = studentService;
    }

    @GetMapping()
    public String getAllStudents(Model model) {
	List<Student> students = studentService.getAll();
	model.addAttribute("students", students);
	return "students/all";
    }

    @GetMapping("/{id}")
    public String getViewStudentGet(@PathVariable int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/student";
    }

}