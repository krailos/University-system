package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.LessonTimeService;
import ua.com.foxminded.krailo.university.service.SubjectService;

@Controller
@RequestMapping("/lessonTimes")
public class LessonTimeController {

    @Autowired
    LessonTimeService lessonTimeService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GroupService groupService;

    @GetMapping()
    public String getLessonTimeStartPage() {
	return "lessonTimes/lessonTimes";
    }

    @GetMapping("/all")
    public String getAllLessonTime(Model model) {
	List<LessonTime> lessonTimes = lessonTimeService.getAll();
	model.addAttribute("lessonTimes", lessonTimes);
	return "lessonTimes/lessonTimesAll";
    }

    @GetMapping("/findLessonTimeById/{id}")
    public String getViewLessonTime(@PathVariable("id") int id, Model model) {
	LessonTime lessonTime = lessonTimeService.getById(id);
	model.addAttribute("lessonTime", lessonTime);
	return "lessonTimes/lessonTimeView";
    }

}
