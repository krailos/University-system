package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getAllGroups(@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
	    @RequestParam(value = "pageId", defaultValue = "1", required = false) int pageId, Model model) {
	int audienceQuantity = groupService.getQuantity();
	int pageQuantity = audienceQuantity % pageSize == 0 ? audienceQuantity / pageSize
		: audienceQuantity / pageSize + 1;
	List<Group> audienceByLimit = groupService.getByPage(pageSize, pageSize * (pageId - 1));
	model.addAttribute("pageQuantity", pageQuantity);
	model.addAttribute("groups", audienceByLimit);
	return "groups/all";
    }

    @GetMapping("/{id}")
    public String getGroup(@PathVariable int id, Model model) {
	Group group = groupService.getById(id);
	model.addAttribute("group", group);
	return "groups/group";
    }

}
