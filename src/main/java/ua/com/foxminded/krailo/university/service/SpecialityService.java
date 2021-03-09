package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
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
	isUniqueSpecialityName(speciality);
	specialityDao.create(speciality);
    }

    public void update(Speciality speciality) {
	log.debug("Update speciality={}", speciality);
	isUniqueSpecialityName(speciality);
	specialityDao.update(speciality);
    }

    public Speciality getById(int id) {
	log.debug("Get department by id={}", id);
	Optional<Speciality> existinSpeciality = specialityDao.findById(id);
	if (existinSpeciality.isPresent()) {
	    return existinSpeciality.get();
	} else {
	    throw new ServiceException(format("speciality with id=%s not exist", id));
	}
    }

    public List<Speciality> getAll() {
	log.debug("Get all specialities");
	return specialityDao.findAll();
    }

    public void delete(Speciality speciality) {
	log.debug("Delete speciality={}", speciality);
	specialityDao.deleteById(speciality.getId());
    }

    private void isUniqueSpecialityName(Speciality speciality) {
	log.debug("is speciality name unique ?");
	Optional<Speciality> existingSpeciality = specialityDao.findByNameAndFacultyId(speciality.getName(),
		speciality.getFaculty().getId());
	if (existingSpeciality.isEmpty()
		|| existingSpeciality.filter(s -> s.getId() == speciality.getId()).isPresent()) {
	    log.debug("speciality name is unique");
	} else {
	    throw new ServiceException(format("speciality name=%s is not unique ", speciality.getName()));
	}

    }

}
