package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;

@Controller
@RequestMapping("/audiences")
public class AudienceController {

    @Autowired
    AudienceService audienceService;

    @GetMapping()
    public String getAudienceStartPage() {
	return "audiences/audiences";
    }

    @GetMapping("/all")
    public String getAllAudiences(Model model) {
	List<Audience> audiences = audienceService.getAll();
	model.addAttribute("audiences", audiences);
	return "audiences/audiencesAll";
    }

    @GetMapping("/findAudienceById/{id}")
    public String getViewAudience(@PathVariable("id") int id, Model model) {
	Audience audience = audienceService.getById(id);
	model.addAttribute("audience", audience);
	return "audiences/audienceView";
    }

}
