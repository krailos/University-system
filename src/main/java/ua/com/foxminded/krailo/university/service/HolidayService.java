package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.HolidayDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Holiday;

@Transactional
@Service
public class HolidayService {

    private static final Logger log = LoggerFactory.getLogger(HolidayService.class);
    private HolidayDao holidayDaoInt;

    public HolidayService(HolidayDao holidayDaoInt) {
	this.holidayDaoInt = holidayDaoInt;
    }

    public void create(Holiday holiday) {
	log.debug("Create holiday={}", holiday);
	holidayDaoInt.create(holiday);
    }

    public void update(Holiday holiday) {
	log.debug("Update holiday={}", holiday);
	holidayDaoInt.update(holiday);
    }

    public Holiday getById(int id) {
	log.debug("Get holiday by id={}", id);
	return holidayDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Holiday whith id=%s not exist", id)));
    }

    public List<Holiday> getAll() {
	log.debug("Get all holidays");
	return holidayDaoInt.getAll();
    }

    public void delete(Holiday holiday) {
	log.debug("Delete holiday={}", holiday);
	holidayDaoInt.delete(holiday);
    }

}
