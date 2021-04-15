package ua.com.foxminded.krailo.university.controllers;

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
    public String getAllAudiences(
	    @RequestParam(value = "pageSize", defaultValue = "${page.defaultPageSize:2}", required = false) int pageSize,
	    @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber, Model model) {
	Paging paging = new Paging(pageSize, pageNumber, audienceService.getQuantity());
	model.addAttribute("pageQuantity", paging.getPageQuantity());
	model.addAttribute("audiences", audienceService.getByPage(paging));
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
    public String saveAudeince(@ModelAttribute("teacher") Audience audience) {
	if (audience.getId() == 0) {
	    audienceService.create(audience);
	} else {
	    audienceService.update(audience);
	}
	return "redirect:/audiences";
    }

    @GetMapping("/delete/{id}")
    public String deleteAudience(@PathVariable int id) {
	audienceService.delete(audienceService.getById(id));
	return "redirect:/audiences";
    }

}
