package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

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
	if (isUniqueSpecialityName(speciality)) {
	    specialityDao.create(speciality);
	}
    }

    public void update(Speciality speciality) {
	if (isUniqueSpecialityName(speciality)) {
	    specialityDao.update(speciality);
	}
    }

    public Speciality getById(int id) {
	return specialityDao.findById(id);
    }

    public List<Speciality> getAll() {
	return specialityDao.findAll();
    }

    public void delete(Speciality speciality) {
	specialityDao.deleteById(speciality.getId());
    }

    private boolean isUniqueSpecialityName(Speciality speciality) {
	Optional<Speciality> existingSpeciality = Optional.ofNullable(
		specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()));
	return (existingSpeciality.isEmpty()
		|| existingSpeciality.filter(s -> s.getId() == speciality.getId()).isPresent());
    }

}
