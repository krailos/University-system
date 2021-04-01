package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Specialty;
import ua.com.foxminded.krailo.university.service.FacultyService;
import ua.com.foxminded.krailo.university.service.SpecialtyService;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private FacultyService facultyService;
    private SpecialtyService speciltyService;

    public FacultyController(FacultyService facultyService, SpecialtyService speciltyService) {
	this.facultyService = facultyService;
	this.speciltyService = speciltyService;
    }

    @GetMapping()
    public String getFacultyStartPage() {
	return "faculties/faculties";
    }

    @GetMapping("/all")
    public String getAllFaculties(Model model) {
	List<Faculty> faculties = facultyService.getAll();
	model.addAttribute("faculties", faculties);
	return "faculties/facultiesAll";
    }

    @GetMapping("/findFacultyById/{id}")
    public String getViewFaculty(@PathVariable("id") int id, Model model) {
	Faculty faculty = facultyService.getById(id);
	List<Specialty> specialties = speciltyService.getByFacultyId(faculty.getId());
	faculty.setSpecialities(specialties);
	model.addAttribute("faculty", faculty);
	return "faculties/facultyView";
    }

}
