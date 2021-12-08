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

import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.service.SubjectService;
import ua.com.foxminded.krailo.university.service.YearService;

@RestController
@RequestMapping("rest/years")
public class YearRestController {

    private YearService yearService;
    private SubjectService subjectService;

    public YearRestController(YearService yearService, SubjectService subjectService) {
	this.yearService = yearService;
	this.subjectService = subjectService;
    }

    @GetMapping
    public List<Year> getAllYears() {
	return yearService.getAll();
    }

    @GetMapping("/{id}")
    public Year getYear(@PathVariable("id") int id) {
	return yearService.getById(id);
    }

    @PostMapping()
    public void saveYear(@RequestBody Year year) {
	yearService.create(year);
    }

    @PutMapping()
    public void editYear(@RequestBody Year year) {
	yearService.create(year);
    }

    @DeleteMapping("/{id}")
    public void deleteYear(@RequestParam int id) {
	yearService.delete(yearService.getById(id));
    }

}
