package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
	this.groupService = groupService;
    }

    @GetMapping
    public String getAllGroups(Model model) {
	List<Group> groups = groupService.getAll();
	model.addAttribute("groups", groups);
	return "groups/all";
    }

    @GetMapping("/{id}")
    public String getGroup(@PathVariable int id, Model model) {
	Group group = groupService.getById(id);
	model.addAttribute("group", group);
	return "groups/group";
    }

}
