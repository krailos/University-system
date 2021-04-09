package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public String getAllStudents(@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
	    @RequestParam(value = "pageId", defaultValue = "1", required = false) int pageId, Model model) {
	int audienceQuantity = studentService.getQuantity();
	int pageQuantity = audienceQuantity % pageSize == 0 ? audienceQuantity / pageSize
		: audienceQuantity / pageSize + 1;
	List<Student> lessonsByLimit = studentService.getByPage(pageSize, pageSize * (pageId - 1));
	model.addAttribute("pageQuantity", pageQuantity);
	model.addAttribute("students", lessonsByLimit);
	return "students/all";
    }

    @GetMapping("/{id}")
    public String getStudent(@PathVariable int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/student";
    }

}