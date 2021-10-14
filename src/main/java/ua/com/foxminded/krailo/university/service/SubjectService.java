package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Subject;

@Transactional
@Service
public class SubjectService {

    private static final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
	this.subjectDao = subjectDao;
    }

    public Subject getById(int id) {
	log.debug("Get subject by id={}", id);
	return subjectDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Subject whith id=%s not exist", id)));
    }

    public List<Subject> getAll() {
	log.debug("Get all subjects");
	return (List<Subject>) subjectDao.findAll();
    }

    public void create(Subject subject) {
	log.debug("Create subject={}", subject);
	subjectDao.save(subject);
    }

    public void delete(Subject subject) {
	log.debug("Delete subject={}", subject);
	subjectDao.delete(subject);
    }

}
