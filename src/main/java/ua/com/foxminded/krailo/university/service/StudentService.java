package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Student;

@Service
@PropertySource("classpath:config.properties")
public class StudentService {

    private StudentDao studentDao;
    private GroupDao groupDao;
    @Value("${model.groupMaxCapacity}")
    private int groupMaxCapacity;

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
	System.out.println(groupMaxCapacity);
	return (groupDao.findById(student.getGroup().getId()).getStudents().size() + 1) <= groupMaxCapacity;
    }
}
