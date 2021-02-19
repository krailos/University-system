package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.Speciality;

@Service
public class SpecialityService {

    private SpecialityDao specialityDao;

    public SpecialityService(SpecialityDao specialityDao) {
	this.specialityDao = specialityDao;
    }

    public void create(Speciality speciality) {
	specialityDao.create(speciality);
    }

    public void update(Speciality speciality) {
	specialityDao.update(speciality);
    }

    public Speciality getById(int id) {
	Speciality speciality = specialityDao.findById(id);
	return speciality;
    }

    public List<Speciality> getAll() {
	List<Speciality> specialities = specialityDao.findAll();
	return specialities;
    }

    public void delete(Speciality speciality) {
	specialityDao.deleteById(speciality.getId());
    }

}
