package ua.com.foxminded.krailo.university.restControllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.YearService;

@RestController
@RequestMapping("reast/groups")
public class GroupRestController {

    private GroupService groupService;
    private YearService yearService;

    public GroupRestController(GroupService groupService, YearService yearService) {
	this.groupService = groupService;
	this.yearService = yearService;
    }

    @GetMapping
    public Page<Group> getAllGroups(Pageable pageable) {
	return groupService.getSelectedPage(pageable);
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable int id) {
	return groupService.getById(id);
    }

    @PostMapping()
    public void saveGroup(@RequestBody Group group) {
	groupService.create(group);
    }

    @PutMapping()
    public void editGroup(@RequestBody Group group) {
	groupService.create(group);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@RequestParam int id) {
	groupService.delete(groupService.getById(id));
    }

}
