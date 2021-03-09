package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class YearService {

    private static final Logger log = LoggerFactory.getLogger(YearService.class);

    private YearDao yearDao;

    public YearService(YearDao yearDao) {
	this.yearDao = yearDao;
    }

    public void create(Year year) {
	log.debug("Create year={}", year);
	yearDao.create(year);
    }

    public void update(Year year) {
	log.debug("Update year={}", year);
	yearDao.update(year);
    }

    public Year getById(int id) {
	log.debug("Get year by id={}", id);
	Optional<Year> existingYear = yearDao.findById(id);
	if (existingYear.isPresent()) {
	    return existingYear.get();
	} else {
	    throw new ServiceException(format("year with id=%s not exist", id));
	}
    }

    public List<Year> getAll() {
	log.debug("Get all years");
	return yearDao.findAll();
    }

    public void delete(Year year) {
	log.debug("Delete year={}", year);
	yearDao.deleteById(year.getId());
    }

}
