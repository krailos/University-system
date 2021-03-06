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

import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.service.HolidayService;

@Controller
@RequestMapping("/holidays")
public class HolidaysController {

    private HolidayService holidayService;

    public HolidaysController(HolidayService holidayService) {
	this.holidayService = holidayService;
    }

    @GetMapping
    public String getAllHolidays(Model model) {
	List<Holiday> holidays = holidayService.getAll();
	model.addAttribute("holidays", holidays);
	return "holidays/all";
    }

    @GetMapping("/{id}")
    public String getHoliday(@PathVariable int id, Model model) {
	Holiday holiday = holidayService.getById(id);
	model.addAttribute("holiday", holiday);
	return "holidays/holiday";
    }

    @GetMapping("/create")
    public String createHoliday(Model model) {
	model.addAttribute("holiday", new Holiday());
	return "holidays/edit";
    }

    @GetMapping("/edit/{id}")
    public String editHoliday(@PathVariable int id, Model model) {
	model.addAttribute("holiday", holidayService.getById(id));
	return "holidays/edit";
    }

    @PostMapping("/save")
    public String saveHoliday(@Valid @ModelAttribute("holiday") Holiday holiday, BindingResult result) {
	if(result.hasErrors()) {
	    return "holidays/edit";
	}
	    holidayService.create(holiday);	
	return "redirect:/holidays";
    }

    @PostMapping("/delete")
    public String deleteHoliday(@RequestParam int id) {
	holidayService.delete(holidayService.getById(id));
	return "redirect:/holidays";
    }

}
