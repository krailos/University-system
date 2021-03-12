package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Speciality;

@Service
public class SpecialityService {

    private static final Logger log = LoggerFactory.getLogger(SpecialityService.class);

    private SpecialityDao specialityDao;

    public SpecialityService(SpecialityDao specialityDao) {
	this.specialityDao = specialityDao;
    }

    public void create(Speciality speciality) {
	log.debug("Create speciality={}", speciality);
	chekSpecialityNameBeUnique(speciality);
	specialityDao.create(speciality);
    }

    public void update(Speciality speciality) {
	log.debug("Update speciality={}", speciality);
	chekSpecialityNameBeUnique(speciality);
	specialityDao.update(speciality);
    }

    public Speciality getById(int id) {
	log.debug("Get department by id={}", id);
	return specialityDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Speciality whith id=%s not exist", id)));
    }

    public List<Speciality> getAll() {
	log.debug("Get all specialities");
	return specialityDao.findAll();
    }

    public void delete(Speciality speciality) {
	log.debug("Delete speciality={}", speciality);
	specialityDao.deleteById(speciality.getId());
    }

    private void chekSpecialityNameBeUnique(Speciality speciality) {
	Optional<Speciality> existingSpeciality = specialityDao.findByNameAndFacultyId(speciality.getName(),
		speciality.getFaculty().getId());
	if (existingSpeciality.isPresent()
		&& existingSpeciality.filter(s -> s.getId() != speciality.getId()).isPresent()) {
	    throw new NotUniqueNameException(format("speciality name=%s is not unique", speciality.getName()));
	}

    }

}
