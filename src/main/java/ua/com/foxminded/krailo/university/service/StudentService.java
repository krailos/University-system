package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@Service
public class StudentService {

    private StudentDao studentDao;
    private GroupDao groupDao;

    public StudentService(StudentDao studentDao, GroupDao groupDao) {
	this.studentDao = studentDao;
	this.groupDao = groupDao;
    }

    public void create(Student student) {
	if (isEnoughtGroupCapacity(student)) {
	    studentDao.create(student);
	}
    }

    public void update(Student student) {
	if (isEnoughtGroupCapacity(student)) {
	    studentDao.update(student);
	}
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

    private boolean isEnoughtGroupCapacity(Student student) {
	Group group = groupDao.findById(student.getId());
	return (groupDao.findById(student.getId()).getStudents().size() + 1) <= group.getCapacity();
    }
}
