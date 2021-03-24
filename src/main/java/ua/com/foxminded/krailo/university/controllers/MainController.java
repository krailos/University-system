package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.StudentService;
import ua.com.foxminded.krailo.university.service.SubjectService;

@Controller
public class MainController {

    @Autowired
    StudentService studentService;
    

    @Autowired
    GroupService groupService;

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

    @GetMapping("/students/formAddStudent")
    public String getFormAddStudent(Model model) {
	Student student = new  Student();
	model.addAttribute("student", student);
	return "formAddStudent";
    }
    
    @PostMapping("/students/createStudent")
    public String createStudent(@ModelAttribute("student") Student student) {
	Group group = groupService.getById(2);
	student.setGroup(group);
	studentService.create(student);
	return "redirect:/students";
    }
    
    @GetMapping("/students/formStudentById")
    public String getFormStudentById() {
	return "formStudentById";
    }
    
    @PostMapping("/students/findStudentById")
    public String findStudentById(@RequestParam("id") int id, Model model) {
	model.addAttribute("studentById", studentService.getById(id));
	return "studentById";
    }
    
    
    @GetMapping("/students/update/{id}")
    public String getFormUpdateStudent (@PathVariable("id") int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "formUpdateStudent";
    }
    
    @PostMapping("/students/update/{id}")
    public String updateStudent (@ModelAttribute("student") Student student) {
	Group group = groupService.getById(2);
	student.setGroup(group);
	studentService.update(student);
	return "redirect:/students";
    }
    
    

}
