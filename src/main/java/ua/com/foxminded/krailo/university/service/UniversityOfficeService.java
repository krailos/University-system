package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Service
public class UniversityOfficeService {

    private static final Logger log = LoggerFactory.getLogger(UniversityOfficeService.class);

    private UniversityOfficeDao universityOfficeDao;

    public UniversityOfficeService(UniversityOfficeDao universityOfficeDao) {
	this.universityOfficeDao = universityOfficeDao;
    }

    public void create(UniversityOffice universityOffice) {
	log.debug("Create universityOffice={}", universityOffice);
	universityOfficeDao.create(universityOffice);
    }

    public void update(UniversityOffice universityOffice) {
	log.debug("Update universityOffice={}", universityOffice);
	universityOfficeDao.update(universityOffice);
    }

    public UniversityOffice getById(int id) {
	log.debug("Get universityOffice by id={}", id);
	Optional<UniversityOffice> existingUniversityOffice = universityOfficeDao.findById(id);
	if (existingUniversityOffice.isPresent()) {
	    return existingUniversityOffice.get();
	} else {
	    throw new ServiceException(format("universityOffice with id=%s not exist", id));
	}

    }

    public List<UniversityOffice> getAll() {
	log.debug("Get all universityOffices");
	return universityOfficeDao.findAll();
    }

    public void delete(UniversityOffice universityOffice) {
	log.debug("Delete universityOffice={}", universityOffice);
	universityOfficeDao.deleteById(universityOffice.getId());
    }

}
