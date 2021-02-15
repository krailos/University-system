package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Student;

@Service
public class StudentService {

    private StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
	this.studentDao = studentDao;
    }

    public void create(Student student) {
	studentDao.create(student);
    }

    public void update(Student student) {
	studentDao.update(student);
    }

    public Student getById(int id) {
	return studentDao.findById(id);
    }

    public List<Student> getAll() {
	return studentDao.findAll();
    }

    public List<Student> getByGroupId(int id) {
	return studentDao.findByGroupId(id);
    }

    public void delete(Student student) {
	studentDao.deleteById(student.getId());
    }
}
