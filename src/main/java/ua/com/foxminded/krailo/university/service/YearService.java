package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class YearService {

    private YearDao yearDao;

    public YearService(YearDao yearDao) {
	this.yearDao = yearDao;
    }

    public void create(Year year) {
	yearDao.create(year);
    }

    public void update(Year year) {
	yearDao.update(year);
    }

    public Year getById(int id) {
	return yearDao.findById(id);
    }

    public List<Year> getAll() {
	return yearDao.findAll();
    }

    public void delete(Year year) {
	yearDao.deleteById(year.getId());
    }

}
