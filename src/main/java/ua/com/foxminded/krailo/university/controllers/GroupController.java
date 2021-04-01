package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.StudentService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private StudentService studentService;
    private GroupService groupService;

    public GroupController(StudentService studentService, GroupService groupService) {
	this.studentService = studentService;
	this.groupService = groupService;
    }

    @GetMapping()
    public String getGroupStartPage() {
	return "groups/groups";
    }

    @GetMapping("/all")
    public String getAllGroups(Model model) {
	List<Group> groups = groupService.getAll();
	model.addAttribute("groups", groups);
	return "groups/groupsAll";
    }

    @GetMapping("/findGroupById/{id}")
    public String getViewGroups(@PathVariable("id") int id, Model model) {
	Group group = groupService.getById(id);
	List<Student> students = studentService.getByGroupId(group.getId());
	group.setStudents(students);
	model.addAttribute("group", group);
	return "groups/groupView";
    }

}
