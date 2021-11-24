package ua.com.foxminded.krailo.university.controllers;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;
    private GroupService groupService;
    private LessonService lessonService;

    public StudentController(StudentService studentService, GroupService groupService, LessonService lessonService) {
	this.studentService = studentService;
	this.groupService = groupService;
	this.lessonService = lessonService;
    }

    @GetMapping
    public String getAllStudents(Model model, Pageable pageable) {
	model.addAttribute("studentsPage", studentService.getSelectedPage(pageable));
	return "students/all";
    }

    @GetMapping("/{id}")
    public String getStudent(@PathVariable int id, Model model) {
	model.addAttribute("student", studentService.getById(id));
	return "students/student";
    }

    @GetMapping("/create")
    public String createStudent(Model model) {
	model.addAttribute("student", new Student());
	model.addAttribute("groups", groupService.getAll());
	return "students/edit";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    model.addAttribute("groups", groupService.getAll());
	    return "students/edit";
	}

	studentService.create(student);
	return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {
	model.addAttribute("student", studentService.getById(id));
	model.addAttribute("groups", groupService.getAll());
	return "students/edit";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("id") int id, Model model) {
	studentService.delete(studentService.getById(id));
	return "redirect:/students";
    }

    @GetMapping("/schedule/{id}")
    public String getSchedule(Model model, @PathVariable("id") int studentId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {
	Student student = studentService.getById(studentId);
	model.addAttribute("student", student);
	model.addAttribute("lessons", lessonService.getLessonsForStudentByPeriod(student, startDate, finishDate));
	model.addAttribute("startDate", startDate);
	model.addAttribute("finishDate", finishDate);
	return "students/schedule";
    }

}