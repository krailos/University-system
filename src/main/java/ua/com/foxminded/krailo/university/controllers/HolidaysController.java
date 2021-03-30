package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.service.HolidayService;

@Controller
@RequestMapping("/holidays")
public class HolidaysController {

    @Autowired
    HolidayService holidayService;
   
    @GetMapping()
    public String getHolidaysStartPage() {
	return "holidays/holidays";
    }

    @GetMapping("/all")
    public String getAllHolidays(Model model) {
	List<Holiday> holidays = holidayService.getAll();
	model.addAttribute("holidays", holidays);
	return "holidays/holidaysAll";
    }

    @GetMapping("/findHolidayById/{id}")
    public String getViewHolidays(@PathVariable("id") int id, Model model) {
	Holiday holiday = holidayService.getById(id);
	model.addAttribute("holiday", holiday);
	return "holidays/holidayView";
    }

}
