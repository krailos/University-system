package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.service.DeansOfficeService;
import ua.com.foxminded.krailo.university.service.FacultyService;

@Controller
@RequestMapping("/deansOffices")
public class DeansOfficeController {

    @Autowired
    DeansOfficeService deansOfficeService;
    @Autowired
    FacultyService facultyService;

    @GetMapping()
    public String getDeansOfficeStartPage() {
	return "deansOffices/deansOffices";
    }

    @GetMapping("/all")
    public String getAllDeansOffices(Model model) {
	List<DeansOffice> deansOffices = deansOfficeService.getAll();
	model.addAttribute("deansOffices", deansOffices);
	return "deansOffices/deansOfficesAll";
    }

    @GetMapping("/findDeansOfficeById/{id}")
    public String getViewDeansOffice(@PathVariable("id") int id, Model model) {
	DeansOffice deansOffice = deansOfficeService.getById(id);
	model.addAttribute("deansOffice", deansOffice);
	return "deansOffices/deansOfficeView";
    }

}
