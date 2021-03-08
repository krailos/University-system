package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Faculty;

@Service
public class FacultyService {
    private static final Logger log = LoggerFactory.getLogger(FacultyService.class);
    private FacultyDao facultyDao;

    public FacultyService(FacultyDao facultyDao) {
	this.facultyDao = facultyDao;
    }

    public void create(Faculty faculty) {
	log.debug("Create faculty={}", faculty);
	facultyDao.create(faculty);
    }

    public void update(Faculty faculty) {
	log.debug("Updte faculty={}", faculty);
	facultyDao.update(faculty);
    }

    public Faculty getById(int id) {
	log.debug("Get faculty by id={}", id);
	Optional<Faculty> existingDepartment = facultyDao.findById(id);
	if (existingDepartment.isPresent()) {
	    return existingDepartment.get();
	} else {
	    throw new ServiceException(format("Faculty with id=%s not exist", id));
	}
    }

    public List<Faculty> getAll() {
	log.debug("Get all faculties");
	return facultyDao.findAll();
    }

    public void delete(Faculty faculty) {
	log.debug("Delete faculty={}", faculty);
	facultyDao.deleteById(faculty.getId());
    }

}
