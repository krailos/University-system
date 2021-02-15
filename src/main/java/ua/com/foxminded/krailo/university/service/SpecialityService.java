package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Speciality;

@Service
public class SpecialityService {

    private SpecialityDao specialityDao;
    private YearDao yearDao;

    public SpecialityService(SpecialityDao specialityDao, YearDao yearDao) {
	this.specialityDao = specialityDao;
	this.yearDao = yearDao;
    }

    public void create(Speciality speciality) {
	specialityDao.create(speciality);
    }

    public void update(Speciality speciality) {
	specialityDao.update(speciality);
    }

    public Speciality getById(int id) {
	Speciality speciality = specialityDao.findById(id);
	addSpecialitiesToFaculty(speciality);
	return speciality;
    }

    public List<Speciality> getAll() {
	List<Speciality> specialities = specialityDao.findAll();
	for (Speciality faculty : specialities) {
	    addSpecialitiesToFaculty(faculty);
	}
	return specialities;
    }

    public void delete(Speciality speciality) {
	specialityDao.deleteById(speciality.getId());
    }

    private void addSpecialitiesToFaculty(Speciality speciality) {
	speciality.setYears(yearDao.findBySpecialityId(speciality.getId()));
    }

}
