package ua.com.foxminded.krailo.university.controllers;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;
    private SubjectService subjectService;
    private LessonService lessonService;
    private VocationService vocationService;

    public TeacherController(TeacherService teacherService, SubjectService subjectService, LessonService lessonService,
	    VocationService vocationService) {
	this.teacherService = teacherService;
	this.subjectService = subjectService;
	this.lessonService = lessonService;
	this.vocationService = vocationService;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
	List<Teacher> teachers = teacherService.getAll();
	model.addAttribute("teachers", teachers);
	return "teachers/all";
    }

    @GetMapping("/{id}")
    public String getTeacher(@PathVariable int id, Model model) {
	Teacher teacher = teacherService.getById(id);
	model.addAttribute("teacher", teacher);
	return "teachers/teacher";
    }

    @GetMapping("/create")
    public String createTeacher(Model model) {
	model.addAttribute("teacher", new Teacher());
	model.addAttribute("subjects", subjectService.getAll());
	return "teachers/edit";
    }

    @PostMapping("/save")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
	teacherService.create(teacher);
	return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacher(@PathVariable int id, Model model) {
	model.addAttribute("teacher", teacherService.getById(id));
	model.addAttribute("subjects", subjectService.getAll());
	return "teachers/edit";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam int id, Model model) {
	teacherService.delete(teacherService.getById(id));
	return "redirect:/teachers";
    }

    @GetMapping("/schedule/{id}")
    public String getSchedule(Model model, @PathVariable("id") int teacherId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {
	Teacher teacher = teacherService.getById(teacherId);
	model.addAttribute("teacher", teacher);
	model.addAttribute("lessons", lessonService.getLessonsByTeacherByPeriod(teacher, startDate, finishDate));
	model.addAttribute("startDate", startDate);
	model.addAttribute("finishDate", finishDate);
	return "teachers/schedule";
    }

    @GetMapping("/vocations/{id}")
    public String getVocation(Model model, @PathVariable("id") int teacherId, @RequestParam String year) {
	Teacher teacher = teacherService.getById(teacherId);
	model.addAttribute("year", year);
	model.addAttribute("teacher", teacher);
	model.addAttribute("vocations",
		vocationService.getByTeacherAndYear(teacher, Year.of(Integer.valueOf(year))));
	return "teachers/vocations";
    }

}
