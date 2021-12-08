package ua.com.foxminded.krailo.university.restControllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@RestController
@RequestMapping("rest/subjects")
public class SubjectRestController {

    private SubjectService subjectService;
    private TeacherService teacherService;

    public SubjectRestController(SubjectService subjectService, TeacherService teacherService) {
	this.subjectService = subjectService;
	this.teacherService = teacherService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
	return subjectService.getAll();
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable int id) {
	return subjectService.getById(id);
    }

    @PostMapping()
    public void createSubject(@RequestBody Subject subject) {
	for (Teacher teacher : subject.getTeachers()) {
	    teacher = teacherService.getById(teacher.getId());
	}
	subjectService.create(subject);
    }

    @PutMapping()
    public void editSubject(@RequestBody Subject subject) {
	for (Teacher teacher : subject.getTeachers()) {
	    teacher = teacherService.getById(teacher.getId());
	}
	subjectService.create(subject);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@RequestParam int id) {
	subjectService.delete(subjectService.getById(id));
    }

}
