package ua.com.foxminded.krailo.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.YearService;
import ua.com.foxminded.krailo.university.util.Paging;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private GroupService groupService;
    private YearService yearService;

    public GroupController(GroupService groupService, YearService yearService) {
	this.groupService = groupService;
	this.yearService = yearService;
    }

    @GetMapping
    public String getAllGroups(
	    @RequestParam(value = "pageSize", defaultValue = "${page.defaultPageSize:2}", required = false) int pageSize,
	    @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber, Model model) {
	Paging paging = new Paging(pageSize, pageNumber, groupService.getQuantity());
	model.addAttribute("pageQuantity", paging.getPageQuantity());
	model.addAttribute("groups", groupService.getByPage(paging));
	return "groups/all";
    }

    @GetMapping("/{id}")
    public String getGroup(@PathVariable int id, Model model) {
	Group group = groupService.getById(id);
	model.addAttribute("group", group);
	return "groups/group";
    }

    @GetMapping("/create")
    public String createGroup(Model model) {
	model.addAttribute("group", new Group());
	model.addAttribute("years", yearService.getAll());
	return "groups/edit";
    }

    @GetMapping("/edit/{id}")
    public String editGroup(@PathVariable int id, Model model) {
	model.addAttribute("group", groupService.getById(id));
	model.addAttribute("years", yearService.getAll());
	return "groups/edit";
    }

    @PostMapping("/save")
    public String saveGroup(@ModelAttribute("group") Group group) {
	if (group.getId() == 0) {
	    groupService.create(group);
	} else {
	    groupService.update(group);
	}
	return "redirect:/groups";
    }

    @PostMapping("/delete")
    public String deleteGroup(@RequestParam int id) {
	groupService.delete(groupService.getById(id));
	return "redirect:/groups";
    }

}
