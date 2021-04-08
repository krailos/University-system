package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

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

    private AudienceService audienceService;

    public AudienceController(AudienceService audienceService) {
	this.audienceService = audienceService;
    }

    @GetMapping
    public String getAllAudiences(Model model) {
	int limit = 2;
	int offset = 0;
	int audienceQuantity = audienceService.getQuantity();
	int pageQuantity = audienceQuantity % limit == 0 ? audienceQuantity / limit : audienceQuantity / limit + 1;
	List<Audience> audiences = audienceService.getAudiencesByPage(limit, offset);
	model.addAttribute("audiences", audiences);
	model.addAttribute("pageQuantity", pageQuantity);
	return "audiences/all";
    }

    @GetMapping("/{id}")
    public String getAudience(@PathVariable int id, Model model) {
	Audience audience = audienceService.getById(id);
	model.addAttribute("audience", audience);
	return "audiences/audience";
    }

    @GetMapping("page/{pageId}")
    public String getAllAudiencesPagination(@PathVariable int pageId, Model model) {
	int limit = 2;
	int offset = 0;
	int audienceQuantity = audienceService.getQuantity();
	int pageQuantity = audienceQuantity % limit == 0 ? audienceQuantity / limit : audienceQuantity / limit + 1;
	if (pageId == 1) {
	} else {
	    offset = (--pageId) * limit;
	}
	System.out.println(pageId);
	List<Audience> list = audienceService.getAudiencesByPage(limit, offset);
	model.addAttribute("audiences", list);
	model.addAttribute("pageQuantity", pageQuantity);
	return "audiences/page";
    }

}
