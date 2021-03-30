package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.service.TimetableService;

@Controller
@RequestMapping("/timetables")
public class TimetableController {

    @Autowired
    TimetableService timetableService;

    @GetMapping()
    public String getTimetableStartPage() {
	return "timetables/timetables";
    }

    @GetMapping("/all")
    public String getAllTimetables(Model model) {
	List<Timetable> timetables = timetableService.getAll();
	model.addAttribute("timetables", timetables);
	return "timetables/timetablesAll";
    }

    @GetMapping("/findTimetableById/{id}")
    public String getViewTimetable(@PathVariable("id") int id, Model model) {
	Timetable timetable = timetableService.getById(id);
	model.addAttribute("timetable", timetable);
	return "timetables/timetableView";
    }

}
