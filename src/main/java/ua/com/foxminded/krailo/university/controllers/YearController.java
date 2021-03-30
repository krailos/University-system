package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Specialty;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.FacultyService;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.SpecialtyService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/years")
public class YearController {
    
    @Autowired
    YearService yearService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GroupService groupService;
    

    @GetMapping()
    public String getYearStartPage() {
	return "years/years";
    }
    
    @GetMapping("/all")
    public String getAllYears(Model model) {
	List<Year> years =  yearService.getAll();
	model.addAttribute("years", years);
	return "years/yearsAll";
    }
    
    @GetMapping("/findYearById/{id}")
    public String getViewYear (@PathVariable("id") int id, Model model) {
	Year year = yearService.getById(id);
	List<Group> groups = groupService.getByYearId(year.getId());
	year.setGroups(groups);
	model.addAttribute("year", year);
	return "years/yearView";
    }
    
    
}
