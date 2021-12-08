package ua.com.foxminded.krailo.university.restControllers;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@RestController
@RequestMapping("rest/teachers")
public class TeacherRestController {

    private TeacherService teacherService;
    private SubjectService subjectService;
    private LessonService lessonService;
    private VocationService vocationService;

    public TeacherRestController(TeacherService teacherService, SubjectService subjectService,
	    LessonService lessonService, VocationService vocationService) {
	this.teacherService = teacherService;
	this.subjectService = subjectService;
	this.lessonService = lessonService;
	this.vocationService = vocationService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
	return teacherService.getAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable int id) {
	return teacherService.getById(id);
    }

    @PostMapping()
    public void saveTeacher(@RequestBody Teacher teacher) {
	teacherService.create(teacher);
    }

    @PutMapping()
    public void editTeacher(@RequestBody Teacher teacher) {
	teacherService.create(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@RequestParam int id) {
	teacherService.delete(teacherService.getById(id));
    }

    @GetMapping("/schedule/{id}")
    public List<Lesson> getSchedule(@PathVariable("id") int teacherId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {
	return lessonService.getLessonsByTeacherByPeriod(teacherService.getById(teacherId), startDate, finishDate);
    }

    @GetMapping("/vocations/{id}")
    public List<Vocation> getVocation(@PathVariable("id") int teacherId, @RequestParam String year) {
	return vocationService.getByTeacherAndYear(teacherService.getById(teacherId), Year.of(Integer.valueOf(year)));
    }

}
