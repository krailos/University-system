package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@Service
public class YearService {

    private static final Logger log = LoggerFactory.getLogger(YearService.class);

    private YearDao yearDao;

    public YearService(YearDao yearDaoInt) {
	this.yearDao = yearDaoInt;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Year year) {
	log.debug("Create year={}", year);
	yearDao.create(year);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Year year) {
	log.debug("Update year={}", year);
	yearDao.update(year);
    }

    public Year getById(int id) {
	log.debug("Get year by id={}", id);
	return yearDao.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Year whith id=%s not exist", id)));
    }

    public List<Year> getAll() {
	log.debug("Get all years");
	return yearDao.getAll();
    }

    public void delete(Year year) {
	log.debug("Delete year={}", year);
	yearDao.delete(year);
    }

}
