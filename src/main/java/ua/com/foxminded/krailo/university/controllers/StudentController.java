package ua.com.foxminded.krailo.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.StudentService;
import ua.com.foxminded.krailo.university.util.Paging;

@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
	this.studentService = studentService;
    }

    @GetMapping
    public String getAllStudents(
	    @RequestParam(defaultValue = "${page.defaultPageSize:2}", required = false) int pageSize,
	    @RequestParam(defaultValue = "1", required = false) int pageNumber, Model model) {
	Paging paging = new Paging(pageSize, pageNumber, studentService.getQuantity());
	model.addAttribute("pageQuantity", paging.getPageQuantity());
	model.addAttribute("students", studentService.getByPage(paging));
	return "students/all";
    }

    @GetMapping("/{id}")
    public String getStudent(@PathVariable int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/student";
    }

}