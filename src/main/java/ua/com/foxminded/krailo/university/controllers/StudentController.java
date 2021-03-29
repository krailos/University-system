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
    public String getStudentsPage() {
	return "students/students";
    }

    @GetMapping("/all")
    public String getAllStudents(Model model) {
	List<Student> students = studentService.getAll();
	model.addAttribute("students", students);
	return "students/studentsAll";
    }

    @GetMapping("/formAddStudent")
    public String getFormAddStudent(Model model) {
	Student student = new Student();
	model.addAttribute("student", student);
	return "students/formAddStudent";
    }

    @PostMapping("/createStudent")
    public String createStudent(@ModelAttribute("student") Student student) {
	studentService.create(student);
	return "redirect:students/students";
    }

    @GetMapping("/formStudentById")
    public String getFormStudentById() {
	return "students/formFindStudentById";
    }
    
    @GetMapping("/findStudentById/{id}")
    public String getViewStudentGet(@PathVariable("id") int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/studentView";
    }
 
    @PostMapping("/findStudentById/")
    public String getViewStudentPost(@RequestParam("id") int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return  "students/studentView";
    }

    @GetMapping("/formUpdate/{id}")
    public String getFormUpdateStudent(@PathVariable("id") int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/formUpdateStudent";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@ModelAttribute("student") Student student) {
	studentService.update(student);
	return "redirect:/students";
    }

    @GetMapping("/formDelete/{id}")
    public String getFormDeleteStudent(@PathVariable("id") int id, Model model) {
	Student student = studentService.getById(id);
	model.addAttribute("student", student);
	return "students/formDeleteStudent";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model model) {
	Student student = studentService.getById(id);
	studentService.delete(student);
	return "redirect:/students";
    }

}
