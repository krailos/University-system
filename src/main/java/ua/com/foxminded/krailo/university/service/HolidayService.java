package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Holiday;

@Transactional
@Service
public class HolidayService {

    private static final Logger log = LoggerFactory.getLogger(HolidayService.class);
    private HolidayDao holidayDao;

    public HolidayService(HolidayDao holidayDao) {
	this.holidayDao = holidayDao;
    }

    public Holiday getById(int id) {
	log.debug("Get holiday by id={}", id);
	return holidayDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Holiday whith id=%s not exist", id)));
    }

    public List<Holiday> getAll() {
	log.debug("Get all holidays");
	return (List<Holiday>) holidayDao.findAll();
    }

    
    public void create(Holiday holiday) {
	log.debug("Create holiday={}", holiday);
	holidayDao.save(holiday);
    }

    public void delete(Holiday holiday) {
	log.debug("Delete holiday={}", holiday);
	holidayDao.delete(holiday);
    }

}
