package ua.com.foxminded.krailo.university.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.service.AudienceService;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonService;
import ua.com.foxminded.krailo.university.service.LessonTimeService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private static final Logger log = LoggerFactory.getLogger(LessonController.class);

    private LessonService lessonService;
    private LessonTimeService lessonTimeService;
    private SubjectService subjectService;
    private AudienceService audienceService;
    private TeacherService teacherService;
    private GroupService groupService;

    public LessonController(LessonService lessonService, LessonTimeService lessonTimeService,
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
    public String getAllLessons(Model model, Pageable pageable) {
	model.addAttribute("lessonsPage", lessonService.getSelectedPage(pageable));
	return "lessons/all";
    }

    @GetMapping("/{id}")
    public String getLesson(@PathVariable int id, Model model) {
	log.debug("get lesson by id={}", id);
	model.addAttribute("lesson", lessonService.getById(id));
	log.debug("lesson by id={} got", id);
	return "lessons/lesson";
    }

    @GetMapping("/create")
    public String createLesson(Model model) {
	model.addAttribute("lesson", new Lesson());
	model.addAttribute("lessonTimes", lessonTimeService.getAll());
	model.addAttribute("subjects", subjectService.getAll());
	model.addAttribute("audiences", audienceService.getAll());
	model.addAttribute("teachers", teacherService.getAll());
	model.addAttribute("groups", groupService.getAll());

	return "lessons/edit";
    }

    @PostMapping("/save")
    public String saveLesson(@Valid @ModelAttribute("lesson") Lesson lesson, BindingResult result) {
	if(result.hasErrors()) {
	    return "lessons/edit";
	}
	lesson.setLessonTime(lessonTimeService.getById(lesson.getLessonTime().getId()));
	lesson.setSubject(subjectService.getById(lesson.getSubject().getId()));
	lesson.setAudience(audienceService.getById(lesson.getAudience().getId()));
	lesson.setTeacher(teacherService.getById(lesson.getTeacher().getId()));
	for (Group group : lesson.getGroups()) {
	    group = groupService.getById(group.getId());
	}
	lessonService.create(lesson);
	return "redirect:/lessons";
    }

    @GetMapping("/edit/{id}")
    public String editLesson(@PathVariable int id, Model model) {
	model.addAttribute("lesson", lessonService.getById(id));
	model.addAttribute("lessonTimes", lessonTimeService.getAll());
	model.addAttribute("subjects", subjectService.getAll());
	model.addAttribute("audiences", audienceService.getAll());
	model.addAttribute("teachers", teacherService.getAll());
	model.addAttribute("groups", groupService.getAll());
	return "lessons/edit";
    }

    @PostMapping("/delete")
    public String deleteLesson(@RequestParam int id, Model model) {
	lessonService.delete(lessonService.getById(id));
	return "redirect:/lessons";
    }

    @GetMapping("/substituteTeacherForm")
    public String substituteTeacherGetForm(Model model) {
	List<Teacher> teachers = teacherService.getAll();
	model.addAttribute("teachers", teachers);
	return "lessons/substituteTeacherForm";
    }

    @PostMapping("/findTeacherForSubstitute")
    public String findTeacherForSubstitute(
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
	    @RequestParam int teacherId, Model model) {
	Teacher substitutedTeacher = teacherService.getById(teacherId);
	List<Teacher> teachersForSubstitite = teacherService.findTeachersForSubstitute(substitutedTeacher, startDate,
		finishDate);
	model.addAttribute("startDate", startDate);
	model.addAttribute("finishDate", finishDate);
	model.addAttribute("teacher", teacherService.getById(teacherId));
	model.addAttribute("teachersForSubstitite", teachersForSubstitite);
	return "lessons/substituteTeacher";
    }

    @PostMapping("/substituteTeacher")
    public String substituteTeacher(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate, @RequestParam int oldId,
	    @RequestParam int newId, Model model) {
	Teacher newTeacher = teacherService.getById(newId);
	Teacher oldTeacher = teacherService.getById(oldId);
	lessonService.substituteTeacher(oldTeacher, newTeacher, startDate, finishDate);
	return "redirect:/lessons";
    }

}
