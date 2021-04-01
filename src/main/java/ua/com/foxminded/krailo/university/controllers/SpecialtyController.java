package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Specialty;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.SpecialtyService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/specialties")
public class SpecialtyController {

    private YearService yearService;

    private SpecialtyService specialtyService;

    public SpecialtyController(YearService yearService, SpecialtyService specialtyService) {
	this.yearService = yearService;
	this.specialtyService = specialtyService;
    }

    @GetMapping()
    public String getFacultyStartPage() {
	return "specialties/specialties";
    }

    @GetMapping("/all")
    public String getAllSpecialties(Model model) {

	List<Specialty> specialties = specialtyService.getAll();

	model.addAttribute("specialties", specialties);
	return "specialties/specialtiesAll";
    }

    @GetMapping("/findSpecialtyById/{id}")
    public String getViewSpecialty(@PathVariable("id") int id, Model model) {

	Specialty specialty = specialtyService.getById(id);

	List<Year> years = yearService.getBySpecialtyId(specialty.getId());
	specialty.setYears(years);
	model.addAttribute("specialty", specialty);
	return "specialties/specialtyView";
    }

}
