package ua.com.foxminded.krailo.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Specialty;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.FacultyService;
import ua.com.foxminded.krailo.university.service.GroupService;
import ua.com.foxminded.krailo.university.service.SpecialtyService;
import ua.com.foxminded.krailo.university.service.StudentService;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.YearService;

@Controller
@RequestMapping("/groups")
public class GroupController {
    
    @Autowired	
    YearService yearService;
    @Autowired
    StudentService studentService;
    @Autowired
    GroupService groupService;
    

    @GetMapping()
    public String getGroupStartPage() {
	return "groups/groups";
    }
    
    @GetMapping("/all")
    public String getAllGroups(Model model) {
	List<Group> groups =  groupService.getAll();
	model.addAttribute("groups", groups);
	return "groups/groupsAll";
    }
    
    @GetMapping("/findGroupById/{id}")
    public String getViewGroups (@PathVariable("id") int id, Model model) {
	Group group = groupService.getById(id);
	List<Student> students = studentService.getByGroupId(group.getId());
	group.setStudents(students);
	model.addAttribute("group", group);
	return "groups/groupView";
    }
    
    
}
