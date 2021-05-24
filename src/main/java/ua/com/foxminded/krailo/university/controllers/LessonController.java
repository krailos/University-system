package ua.com.foxminded.krailo.university.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import ua.com.foxminded.krailo.university.util.Paging;

@Controller
@RequestMapping("/lessons")
public class LessonController {

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
    public String getAllLessons(
	    @RequestParam(defaultValue = "${page.defaultPageSize:2}", required = false) int pageSize,
	    @RequestParam(defaultValue = "1", required = false) int pageNumber, Model model) {
	Paging paging = new Paging(pageSize, pageNumber, lessonService.getQuantity());
	model.addAttribute("pageQuantity", paging.getPageQuantity());
	model.addAttribute("lessons", lessonService.getByPage(paging));
	return "lessons/all";
    }

    @GetMapping("/{id}")
    public String getLesson(@PathVariable int id, Model model) {
	model.addAttribute("lesson", lessonService.getById(id));
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
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson) {
	lesson.setLessonTime(lessonTimeService.getById(lesson.getLessonTime().getId()));
	lesson.setSubject(subjectService.getById(lesson.getSubject().getId()));
	lesson.setAudience(audienceService.getById(lesson.getAudience().getId()));
	lesson.setTeacher(teacherService.getById(lesson.getTeacher().getId()));
	for (Group group : lesson.getGroups()) {
	    group = groupService.getById(group.getId());
	}
	if (lesson.getId() == 0) {
	    lessonService.create(lesson);
	} else {
	    lessonService.update(lesson);
	}
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
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate substituteDate, @RequestParam int id,
	    Model model) {
	List<Teacher> teachers = teacherService.getAll();
	List<Teacher> teachersForSubstitite = teachers.stream()
		.filter(t -> lessonService.isTeacherReplaced(id, t.getId(), substituteDate))
		.collect(Collectors.toList());
	model.addAttribute("substituteDate", substituteDate);
	model.addAttribute("teacher", teacherService.getById(id));
	model.addAttribute("teachersForSubstitite", teachersForSubstitite);
	return "lessons/substituteTeacher";
    }

    @PostMapping("/substituteTeacher")
    public String substituteTeacher(
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate substituteDate,
	    @RequestParam int oldId, @RequestParam int newId, Model model) {
	Teacher newTeacher = teacherService.getById(newId);
	lessonService.getLessonsForTeacherByDate(teacherService.getById(oldId), substituteDate).stream()
		.peek(l -> l.setTeacher(newTeacher)).forEach(lessonService::update);
	return "redirect:/lessons";
    }

}
