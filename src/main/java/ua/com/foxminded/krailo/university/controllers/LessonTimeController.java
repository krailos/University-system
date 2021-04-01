package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.service.LessonTimeService;

@Controller
@RequestMapping("/lessonTimes")
public class LessonTimeController {

    private LessonTimeService lessonTimeService;

    public LessonTimeController(LessonTimeService lessonTimeService) {
	this.lessonTimeService = lessonTimeService;
    }

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
