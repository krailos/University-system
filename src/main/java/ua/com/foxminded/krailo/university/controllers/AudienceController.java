package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;

@Controller
@RequestMapping("/audiences")
public class AudienceController {

    private AudienceService audienceService;

    public AudienceController(AudienceService audienceService) {
	this.audienceService = audienceService;
    }

    @GetMapping
    public String getAllAudiences(@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
	    @RequestParam(value = "pageId", defaultValue = "1", required = false) int pageId, Model model) {
	int audienceQuantity = audienceService.getQuantity();
	int pageQuantity = audienceQuantity % pageSize == 0 ? audienceQuantity / pageSize
		: audienceQuantity / pageSize + 1;
	List<Audience> audienceByLimit = audienceService.getByPage(pageSize, pageSize * (pageId - 1));
	model.addAttribute("pageQuantity", pageQuantity);
	model.addAttribute("audiences", audienceByLimit);
	return "audiences/all";
    }

    @GetMapping("/{id}")
    public String getAudience(@PathVariable int id, Model model) {
	Audience audience = audienceService.getById(id);
	model.addAttribute("audience", audience);
	return "audiences/audience";
    }


}
