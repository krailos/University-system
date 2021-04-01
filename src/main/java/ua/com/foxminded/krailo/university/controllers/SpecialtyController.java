package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.SpecialityService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    private YearService yearService;

    private SpecialityService specialtyService;

    public SpecialtyController(YearService yearService, SpecialityService specialtyService) {
	this.yearService = yearService;
	this.specialtyService = specialtyService;
    }

    @GetMapping()
    public String getFacultyStartPage() {
	return "specialties/specialties";
    }

    @GetMapping("/all")
    public String getAllSpecialties(Model model) {

	List<Speciality> specialties = specialtyService.getAll();

	model.addAttribute("specialties", specialties);
	return "specialties/specialtiesAll";
    }

    @GetMapping("/findSpecialtyById/{id}")
    public String getViewSpecialty(@PathVariable("id") int id, Model model) {

	Speciality specialty = specialtyService.getById(id);

	List<Year> years = yearService.getBySpecialtyId(specialty.getId());
	specialty.setYears(years);
	model.addAttribute("specialty", specialty);
	return "specialties/specialtyView";
    }

}
