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
import ua.com.foxminded.krailo.university.model.Specialty;

@Service
public class SpecialtyService {

    private static final Logger log = LoggerFactory.getLogger(SpecialtyService.class);

    private SpecialityDao specialityDao;

    public SpecialtyService(SpecialityDao specialityDao) {
	this.specialityDao = specialityDao;
    }

    public void create(Specialty speciality) {
	log.debug("Create speciality={}", speciality);
	chekSpecialityNameBeUnique(speciality);
	specialityDao.create(speciality);
    }

    public void update(Specialty speciality) {
	log.debug("Update speciality={}", speciality);
	chekSpecialityNameBeUnique(speciality);
	specialityDao.update(speciality);
    }

    public Specialty getById(int id) {
	log.debug("Get department by id={}", id);
	return specialityDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Speciality whith id=%s not exist", id)));
    }

    public List<Specialty> getAll() {
	log.debug("Get all specialities");
	return specialityDao.findAll();
    }
    
    public List<Specialty> getByFacultyId(int facultyId) {
	log.debug("Get specialities by Faculty id={}", facultyId);
	return specialityDao.findByFacultyId(facultyId);
    }

    public void delete(Specialty speciality) {
	log.debug("Delete speciality={}", speciality);
	specialityDao.deleteById(speciality.getId());
    }

    private void chekSpecialityNameBeUnique(Specialty speciality) {
	Optional<Specialty> existingSpeciality = specialityDao.findByNameAndFacultyId(speciality.getName(),
		speciality.getFaculty().getId());
	if (existingSpeciality.filter(s -> s.getId() != speciality.getId()).isPresent()) {
	    throw new NotUniqueNameException(format("speciality name=%s is not unique", speciality.getName()));
	}

    }

}
