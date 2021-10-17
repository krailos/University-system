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

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.YearService;

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
    public String getAllGroups(Model model, Pageable pageable) {
	model.addAttribute("groupsPage", groupService.getSelectedPage(pageable));
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

	groupService.create(group);

	return "redirect:/groups";
    }

    @PostMapping("/delete")
    public String deleteGroup(@RequestParam int id) {
	groupService.delete(groupService.getById(id));
	return "redirect:/groups";
    }

}
