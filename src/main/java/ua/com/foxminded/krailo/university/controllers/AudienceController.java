package ua.com.foxminded.krailo.university.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;
import ua.com.foxminded.krailo.university.util.Paging;

@Controller
@RequestMapping("/audiences")
public class AudienceController {

    private AudienceService audienceService;

    public AudienceController(AudienceService audienceService) {
	this.audienceService = audienceService;
    }

    @GetMapping
    public String getAllAudiences (Model model, Pageable  pageable) {
	model.addAttribute("audiencesPage", audienceService.getSelectedPage(pageable));
	return "audiences/all";
    }

    @GetMapping("/{id}")
    public String getAudience(@PathVariable int id, Model model) {
	Audience audience = audienceService.getById(id);
	model.addAttribute("audience", audience);
	return "audiences/audience";
    }

    @GetMapping("/create")
    public String createAudience(Model model) {
	model.addAttribute("audience", new Audience());
	return "audiences/edit";
    }

    @GetMapping("/edit/{id}")
    public String editAudience(@PathVariable int id, Model model) {
	model.addAttribute("audience", audienceService.getById(id));
	return "audiences/edit";
    }

    @PostMapping("/save")
    public String saveAudeince(@ModelAttribute("audience") Audience audience) {
	if (audience.getId() == 0) {
	    audienceService.create(audience);
	} else {
	    audienceService.update(audience);
	}
	return "redirect:/audiences";
    }

    @PostMapping("/delete")
    public String deleteAudience(@RequestParam int id) {
	audienceService.delete(audienceService.getById(id));
	return "redirect:/audiences";
    }

}
