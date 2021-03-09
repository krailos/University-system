package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Subject;

@Service
public class SubjectService {

    private static final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
	this.subjectDao = subjectDao;
    }

    public void create(Subject subject) {
	log.debug("Create subject={}", subject);
	subjectDao.create(subject);
    }

    public void update(Subject subject) {
	log.debug("Update subject={}", subject);
	subjectDao.update(subject);
    }

    public Subject getById(int id) {
	log.debug("Get subject by id={}", id);
	Optional<Subject> existingSubject = subjectDao.findById(id);
	if (existingSubject.isPresent()) {
	    return existingSubject.get();
	} else {
	    throw new ServiceException(format("subject with id=%s not exist", id));
	}
    }

    public List<Subject> getAll() {
	log.debug("Get all subjects");
	return subjectDao.findAll();

    }

    public void delete(Subject subject) {
	log.debug("Delete subject={}", subject);
	subjectDao.deleteById(subject.getId());
    }

}
