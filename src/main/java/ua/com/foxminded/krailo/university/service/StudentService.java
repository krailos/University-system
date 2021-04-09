package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.GroupOverflowException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@Service
@PropertySource("classpath:config.properties")
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private StudentDao studentDao;
    private GroupDao groupDao;
    @Value("${group.maxSize}")
    private int groupMaxSize;

    public StudentService(StudentDao studentDao, GroupDao groupDao) {
	this.studentDao = studentDao;
	this.groupDao = groupDao;
    }

    public void create(Student student) {
	log.debug("Create student={}", student);
	checkGroupCapacityNotTooBig(student);
	studentDao.create(student);
    }

    public void update(Student student) {
	log.debug("Update student={}", student);
	checkGroupCapacityNotTooBig(student);
	studentDao.update(student);
    }

    public Student getById(int id) {
	log.debug("Get student by id={}", id);
	return studentDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Student whith id=%s not exist", id)));
    }

    public List<Student> getAll() {
	log.debug("Get all students");
	return studentDao.findAll();
    }

    public List<Student> getByGroupId(int id) {
	log.debug("get students  by groupId={}", id);
	return studentDao.findByGroupId(id);
    }

    public void delete(Student student) {
	log.debug("Delete student={}", student);
	studentDao.deleteById(student.getId());
    }

    private void checkGroupCapacityNotTooBig(Student student) {
	Group existingGroup = groupDao.findById(student.getGroup().getId())
		.orElseThrow(() -> new EntityNotFoundException(
			"group for this student not found, groupId=" + student.getGroup().getId()));
	if (existingGroup.getStudents().size() >= groupMaxSize) {
	    throw new GroupOverflowException("group capacity more then groupMaxSize=" + groupMaxSize);
	}
    }

    public int getQuantity() {
	return studentDao.findQuantity();

    }

    public List<Student> getByPage(int limit, int offset) {
	return studentDao.findWithLimit(limit, offset);
    }
}
