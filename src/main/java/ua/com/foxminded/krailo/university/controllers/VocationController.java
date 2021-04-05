package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.service.VocationService;

@Controller
@RequestMapping("/vocations")
public class VocationController {

    private VocationService vocationService;

    public VocationController(VocationService vocationService) {
	this.vocationService = vocationService;
    }

    @GetMapping()
    public String getAllVocations(Model model) {
	List<Vocation> vocations = vocationService.getAll();
	model.addAttribute("vocations", vocations);
	return "vocations/all";
    }

    @GetMapping("/{id}")
    public String getViewVocation(@PathVariable("id") int id, Model model) {
	Vocation vocation = vocationService.getById(id);
	model.addAttribute("vocation", vocation);
	return "vocations/vocation";
    }

}
