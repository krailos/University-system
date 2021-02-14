package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.model.Holiday;

@Service
public class HolidayService {

    private HolidayDao holidayDao;

    public HolidayService(HolidayDao holidayDao) {
	this.holidayDao = holidayDao;
    }

    public void create(Holiday holiday) {
	holidayDao.create(holiday);
    }

    public void update(Holiday holiday) {
	holidayDao.update(holiday);
    }

    public Holiday getById(int id) {
	return holidayDao.findById(id);
    }

    public List<Holiday> getAll() {
	return holidayDao.findAll();
    }

    public void delete(Holiday holiday) {
	holidayDao.deleteById(holiday.getId());
    }

}
