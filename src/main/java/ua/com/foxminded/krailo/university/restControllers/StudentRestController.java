package ua.com.foxminded.krailo.university.restControllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.StudentService;

@RestController
@RequestMapping("rest/students")
public class StudentRestController {

    private StudentService studentService;
    private GroupService groupService;
    private LessonService lessonService;

    public StudentRestController(StudentService studentService, GroupService groupService,
	    LessonService lessonService) {
	this.studentService = studentService;
	this.groupService = groupService;
	this.lessonService = lessonService;
    }

    @GetMapping
    public Page<Student> getAllStudents(Pageable pageable) {
	return studentService.getSelectedPage(pageable);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
	return studentService.getById(id);
    }

    @PostMapping()
    public void saveStudent(@RequestBody Student student) {
	studentService.create(student);
    }

    @PutMapping()
    public void editStudent(@RequestBody Student student) {
	studentService.create(student);
    }

    @DeleteMapping()
    public void deleteStudent(@RequestParam int id) {
	studentService.delete(studentService.getById(id));
    }

    @GetMapping("/schedule/{id}")
    public List<Lesson> getSchedule(@PathVariable("id") int studentId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {
	return lessonService.getLessonsForStudentByPeriod(studentService.getById(studentId), startDate, finishDate);
    }

}