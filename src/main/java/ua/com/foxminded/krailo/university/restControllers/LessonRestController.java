package ua.com.foxminded.krailo.university.restControllers;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.AudienceService;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.LessonTimeService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@RestController
@RequestMapping("rest/lessons")
public class LessonRestController {

    private static final Logger log = LoggerFactory.getLogger(LessonRestController.class);

    private LessonService lessonService;
    private LessonTimeService lessonTimeService;
    private SubjectService subjectService;
    private AudienceService audienceService;
    private TeacherService teacherService;
    private GroupService groupService;

    public LessonRestController(LessonService lessonService, LessonTimeService lessonTimeService,
	    SubjectService subjectService, AudienceService audienceService, TeacherService teacherService,
	    GroupService groupService) {
	this.lessonService = lessonService;
	this.lessonTimeService = lessonTimeService;
	this.subjectService = subjectService;
	this.audienceService = audienceService;
	this.teacherService = teacherService;
	this.groupService = groupService;
    }

    @GetMapping
    public Page<Lesson> getAllLessons(Pageable pageable) {
	return lessonService.getSelectedPage(pageable);
    }

    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable int id) {
	return lessonService.getById(id);
    }

    @PostMapping()
    public void saveLesson(@RequestBody Lesson lesson) {
	lesson.setLessonTime(lessonTimeService.getById(lesson.getLessonTime().getId()));
	lesson.setSubject(subjectService.getById(lesson.getSubject().getId()));
	lesson.setAudience(audienceService.getById(lesson.getAudience().getId()));
	lesson.setTeacher(teacherService.getById(lesson.getTeacher().getId()));
	for (Group group : lesson.getGroups()) {
	    group = groupService.getById(group.getId());
	}
	lessonService.create(lesson);
    }

    @PutMapping()
    public void editLesson(@RequestBody Lesson lesson) {
	lesson.setLessonTime(lessonTimeService.getById(lesson.getLessonTime().getId()));
	lesson.setSubject(subjectService.getById(lesson.getSubject().getId()));
	lesson.setAudience(audienceService.getById(lesson.getAudience().getId()));
	lesson.setTeacher(teacherService.getById(lesson.getTeacher().getId()));
	for (Group group : lesson.getGroups()) {
	    group = groupService.getById(group.getId());
	}
	lessonService.create(lesson);
    }

    @DeleteMapping()
    public void deleteLesson(@RequestParam int id) {
	lessonService.delete(lessonService.getById(id));
    }

    @PutMapping("/substituteTeacher")
    public void substituteTeacher(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate, @RequestParam int oldId,
	    @RequestParam int newId) {
	Teacher newTeacher = teacherService.getById(newId);
	Teacher oldTeacher = teacherService.getById(oldId);
	lessonService.substituteTeacher(oldTeacher, newTeacher, startDate, finishDate);
    }

}
