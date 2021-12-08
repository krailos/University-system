package ua.com.foxminded.krailo.university.restControllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.service.TeacherService;
import ua.com.foxminded.krailo.university.service.VocationService;

@RestController
@RequestMapping("rest/vocations")
public class VocationRestController {

    private VocationService vocationService;
    private TeacherService teacherService;

    public VocationRestController(VocationService vocationService, TeacherService teacherService) {
	this.vocationService = vocationService;
	this.teacherService = teacherService;
    }

    @GetMapping
    public List<Vocation> getAllVocations() {
	return vocationService.getAll();
    }

    @GetMapping("/{id}")
    public Vocation getVocation(@PathVariable("id") int id) {
	return vocationService.getById(id);
    }

    @PostMapping()
    public void saveVocation(@RequestBody Vocation vocation) {
	vocationService.create(vocation);
    }

    @PutMapping()
    public void editVocation(@RequestBody Vocation vocation) {
	vocationService.create(vocation);
    }

    @DeleteMapping("/{id}")
    public void deleteVocation(@RequestParam int id) {
	vocationService.delete(vocationService.getById(id));
    }

}
