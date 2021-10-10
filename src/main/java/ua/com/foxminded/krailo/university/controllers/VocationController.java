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

import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@Controller
@RequestMapping("/vocations")
public class VocationController {

    private VocationService vocationService;
    private TeacherService teacherService;

    public VocationController(VocationService vocationService, TeacherService teacherService) {
	this.vocationService = vocationService;
	this.teacherService = teacherService;
    }

    @GetMapping
    public String getAllVocations(Model model) {
	List<Vocation> vocations = vocationService.getAll();
	model.addAttribute("vocations", vocations);
	return "vocations/all";
    }

    @GetMapping("/{id}")
    public String getVocation(@PathVariable("id") int id, Model model) {
	Vocation vocation = vocationService.getById(id);
	model.addAttribute("vocation", vocation);
	return "vocations/vocation";
    }

    @GetMapping("/create")
    public String createVocation(Model model) {
	model.addAttribute("vocation", new Vocation());
	model.addAttribute("teachers", teacherService.getAll());
	return "vocations/edit";
    }

    @GetMapping("/edit/{id}")
    public String editVocation(@PathVariable int id, Model model) {
	model.addAttribute("vocation", vocationService.getById(id));
	model.addAttribute("teachers", teacherService.getAll());
	return "vocations/edit";
    }

    @PostMapping("/save")
    public String saveVocation(@ModelAttribute("vocation") Vocation vocation) {	
	    vocationService.create(vocation);
	return "redirect:/vocations";
    }

    @PostMapping("/delete")
    public String deleteVocation(@RequestParam int id) {
	vocationService.delete(vocationService.getById(id));
	return "redirect:/vocations";
    }

}
