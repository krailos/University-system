package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Teacher;

@Service
public class TeacherService {

    private TeacherDao teacherDao;

    public TeacherService(TeacherDao teacherDao) {
	this.teacherDao = teacherDao;
    }

    public void create(Teacher teacher) {
	teacherDao.create(teacher);
    }

    public void update(Teacher teacher) {
	teacherDao.update(teacher);
    }

    public Teacher getById(int id) {
	return teacherDao.findById(id);
    }

    public List<Teacher> getAll() {
	return teacherDao.findAll();
    }

    public void delete(Teacher teacher) {
	teacherDao.deleteById(teacher.getId());
    }

}
