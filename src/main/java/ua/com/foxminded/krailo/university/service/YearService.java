package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class YearService {

    private YearDao yearDao;
    private GroupDao groupDao;

    public YearService(YearDao yearDao, GroupDao groupDao) {
	this.yearDao = yearDao;
	this.groupDao = groupDao;
    }

    public void create(Year year) {
	yearDao.create(year);
    }

    public void update(Year year) {
	yearDao.update(year);
    }

    public Year getById(int id) {
	Year year = yearDao.findById(id);
	addGroupsToYear(year);
	return year;
    }

    public List<Year> getAll() {
	List<Year> years = yearDao.findAll();
	for (Year year : years) {
	    addGroupsToYear(year);
	}
	return years;
    }

    public void delete(Year year) {
	yearDao.deleteById(year.getId());
    }

    private void addGroupsToYear(Year year) {
	year.setGroups(groupDao.findByYearId(year.getId()));
    }

}
