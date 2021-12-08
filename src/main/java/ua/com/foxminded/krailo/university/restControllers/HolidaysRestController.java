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

import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.service.HolidayService;

@RestController
@RequestMapping("rest/holidays")
public class HolidaysRestController {

    private HolidayService holidayService;

    public HolidaysRestController(HolidayService holidayService) {
	this.holidayService = holidayService;
    }

    @GetMapping
    public List<Holiday> getAllHolidays() {
	return holidayService.getAll();
    }

    @GetMapping("/{id}")
    public Holiday getHoliday(@PathVariable int id) {
	return holidayService.getById(id);
    }

    @PostMapping()
    public void saveHoliday(@RequestBody Holiday holiday) {
	holidayService.create(holiday);
    }

    @PutMapping()
    public void editHoliday(@RequestBody Holiday holiday) {
	holidayService.create(holiday);
    }

    @DeleteMapping("/{id}")
    public void deleteHoliday(@RequestParam int id) {
	holidayService.delete(holidayService.getById(id));
    }
}
