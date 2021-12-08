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

import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.service.LessonTimeService;

@RestController
@RequestMapping("rest/lessonTimes")
public class LessonRestTimeController {

    private LessonTimeService lessonTimeService;

    public LessonRestTimeController(LessonTimeService lessonTimeService) {
	this.lessonTimeService = lessonTimeService;
    }

    @GetMapping
    public List<LessonTime> getAllLessonTime() {
	return lessonTimeService.getAll();
    }

    @GetMapping("/{id}")
    public LessonTime getHoliday(@PathVariable int id) {
	return lessonTimeService.getById(id);
    }

    @PostMapping()
    public void saveLessonTime(@RequestBody LessonTime lessonTime) {
	lessonTimeService.create(lessonTime);
    }

    @PutMapping()
    public void editLessonTime(@RequestBody LessonTime lessonTime) {
	lessonTimeService.create(lessonTime);
    }

    @DeleteMapping("/{id}")
    public void deleteLessonTime(@RequestParam int id) {
	lessonTimeService.delete(lessonTimeService.getById(id));
    }

}
