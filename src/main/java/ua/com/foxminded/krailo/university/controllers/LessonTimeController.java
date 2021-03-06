package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.service.LessonTimeService;

@Controller
@RequestMapping("/lessonTimes")
public class LessonTimeController {

    private LessonTimeService lessonTimeService;

    public LessonTimeController(LessonTimeService lessonTimeService) {
	this.lessonTimeService = lessonTimeService;
    }

    @GetMapping
    public String getAllLessonTime(Model model) {
	List<LessonTime> lessonTimes = lessonTimeService.getAll();
	model.addAttribute("lessonTimes", lessonTimes);
	return "lessonTimes/all";
    }

    @GetMapping("/{id}")
    public String getLessonTime(@PathVariable("id") int id, Model model) {
	LessonTime lessonTime = lessonTimeService.getById(id);
	model.addAttribute("lessonTime", lessonTime);
	return "lessonTimes/lessonTime";
    }

    @GetMapping("/create")
    public String createLessonTime(Model model) {
	model.addAttribute("lessonTime", new LessonTime());
	return "lessonTimes/edit";
    }

    @GetMapping("/edit/{id}")
    public String editLessonTime(@PathVariable int id, Model model) {
	model.addAttribute("lessonTime", lessonTimeService.getById(id));
	return "lessonTimes/edit";
    }

    @PostMapping("/save")
    public String saveLessonTimes(@Valid @ModelAttribute("lessonTime") LessonTime lessonTime, BindingResult result) {
	if(result.hasErrors()) {
	    return "lessonTimes/edit";
	}
	lessonTimeService.create(lessonTime);
	return "redirect:/lessonTimes";
    }

    @PostMapping("/delete")
    public String deleteLessonTime(@RequestParam int id) {
	lessonTimeService.delete(lessonTimeService.getById(id));
	return "redirect:/lessonTimes";
    }

}
