package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
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
	return universityOfficeDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("UniversityOffice whith id=%s not exist", id)));
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