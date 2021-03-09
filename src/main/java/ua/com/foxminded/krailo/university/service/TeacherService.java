package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Teacher;

@Service
public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private TeacherDao teacherDao;

    public TeacherService(TeacherDao teacherDao) {
	this.teacherDao = teacherDao;
    }

    public void create(Teacher teacher) {
	log.debug("Create teacher={}", teacher);
	teacherDao.create(teacher);
    }

    public void update(Teacher teacher) {
	log.debug("Update teacher={}", teacher);
	teacherDao.update(teacher);
    }

    public Teacher getById(int id) {
	log.debug("Get teacher by id={}", id);
	Optional<Teacher> existingTeacher = teacherDao.findById(id);
	if (existingTeacher.isPresent()) {
	    return existingTeacher.get();
	} else {
	    throw new ServiceException(format("teacher with id=%s not exist", id));
	}
    }

    public List<Teacher> getAll() {
	log.debug("Get all teachers");
	return teacherDao.findAll();
    }

    public void delete(Teacher teacher) {
	log.debug("Delete teacher={}", teacher);
	teacherDao.deleteById(teacher.getId());
    }

}
