package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/years")
public class YearController {

    private YearService yearService;
    private SubjectService subjectService;

    public YearController(YearService yearService, SubjectService subjectService) {
	this.yearService = yearService;
	this.subjectService = subjectService;
    }

    @GetMapping
    public String getAllYears(Model model) {
	List<Year> years = yearService.getAll();
	model.addAttribute("years", years);
	return "years/all";
    }

    @GetMapping("/{id}")
    public String getYear(@PathVariable("id") int id, Model model) {
	Year year = yearService.getById(id);
	model.addAttribute("year", year);
	return "years/year";
    }

    @GetMapping("/create")
    public String createYear(Model model) {
	model.addAttribute("year", new Year());
	model.addAttribute("subjects", subjectService.getAll());
	return "years/edit";
    }

    @GetMapping("/edit/{id}")
    public String editYear(@PathVariable int id, Model model) {
	model.addAttribute("year", yearService.getById(id));
	model.addAttribute("subjects", subjectService.getAll());
	return "years/edit";
    }

    @PostMapping("/save")
    public String saveYear(@ModelAttribute("year") Year year) {
	yearService.create(year);
	return "redirect:/years";
    }

    @PostMapping("/delete")
    public String deleteYear(@RequestParam int id) {
	yearService.delete(yearService.getById(id));
	return "redirect:/years";
    }

}
