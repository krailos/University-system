package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.model.Subject;

@Service
public class SubjectService {

    private SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
	this.subjectDao = subjectDao;
    }

    public void create(Subject subject) {
	subjectDao.create(subject);
    }

    public void update(Subject subject) {
	subjectDao.update(subject);
    }

    public Subject getById(int id) {
	return subjectDao.findById(id);
    }

    public List<Subject> getAll() {
	return subjectDao.findAll();

    }

    public void delete(Subject subject) {
	subjectDao.deleteById(subject.getId());
    }

}
