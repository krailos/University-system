package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/years")
public class YearController {

    private YearService yearService;

    private GroupService groupService;

    public YearController(YearService yearService, GroupService groupService) {
	this.yearService = yearService;
	this.groupService = groupService;
    }

    @GetMapping()
    public String getYearStartPage() {
	return "years/years";
    }

    @GetMapping("/all")
    public String getAllYears(Model model) {
	List<Year> years = yearService.getAll();
	model.addAttribute("years", years);
	return "years/yearsAll";
    }

    @GetMapping("/findYearById/{id}")
    public String getViewYear(@PathVariable("id") int id, Model model) {
	Year year = yearService.getById(id);
	List<Group> groups = groupService.getByYearId(year.getId());
	year.setGroups(groups);
	model.addAttribute("year", year);
	return "years/yearView";
    }

}
