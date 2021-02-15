package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Subject;

@Service
public class SubjectService {

    private SubjectDao subjectDao;
    private TeacherDao teacherDao;

    public SubjectService(SubjectDao subjectDao, TeacherDao teacherDao) {
	this.subjectDao = subjectDao;
	this.teacherDao = teacherDao;
    }

    public void create(Subject subject) {
	subjectDao.create(subject);
    }

    public void update(Subject subject) {
	subjectDao.update(subject);
    }

    public Subject getById(int id) {
	Subject subject = subjectDao.findById(id);
	addTeachersToSubject(subject);
	return subject;
    }

    public List<Subject> getAll() {
	List<Subject> subjects = subjectDao.findAll();
	for (Subject subject : subjects) {
	    addTeachersToSubject(subject);
	}
	return subjects;
    }

    public void delete(Subject subject) {
	subjectDao.deleteById(subject.getId());
    }

    private void addTeachersToSubject(Subject subject) {
	subject.setTeachers(teacherDao.findBySubjectId(subject.getId()));
    }

}
