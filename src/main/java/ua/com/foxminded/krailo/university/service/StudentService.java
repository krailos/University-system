package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.GroupDao;
import ua.com.foxminded.krailo.university.dao.interf.StudentDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.GroupOverflowException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@Transactional
@Service
@PropertySource("classpath:config.properties")
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private StudentDao studentDaoInt;
    private GroupDao groupDaoInt;
    @Value("${group.maxSize}")
    private int groupMaxSize;

    public StudentService(StudentDao studentDaoInt, GroupDao groupDaoInt) {
	this.studentDaoInt = studentDaoInt;
	this.groupDaoInt = groupDaoInt;
    }

    public void create(Student student) {
	log.debug("Create student={}", student);
	checkGroupCapacityNotTooBig(student);
	studentDaoInt.create(student);
    }

    public void update(Student student) {
	log.debug("Update student={}", student);
	checkGroupCapacityNotTooBig(student);
	studentDaoInt.update(student);
    }

    public Student getById(int id) {
	log.debug("Get student by id={}", id);
	return studentDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Student whith id=%s not exist", id)));
    }

    public List<Student> getAll() {
	log.debug("Get all students");
	return studentDaoInt.getAll();
    }

    public List<Student> getByGroup(Group group) {
	log.debug("get students  by groupId={}", group.getId());
	return studentDaoInt.getByGroup(group);
    }

    public void delete(Student student) {
	log.debug("Delete student={}", student);
	studentDaoInt.delete(student);
    }

    private void checkGroupCapacityNotTooBig(Student student) {
	Group existingGroup = groupDaoInt.getById(student.getGroup().getId())
		.orElseThrow(() -> new EntityNotFoundException(
			"group for this student not found, groupId=" + student.getGroup().getId()));
	if (existingGroup.getStudents().size() >= groupMaxSize) {
	    throw new GroupOverflowException("group capacity more then groupMaxSize=" + groupMaxSize);
	}
    }

    public int getQuantity() {
	return studentDaoInt.count();

    }

    public Page<Student> getSelectedPage(Pageable pageable) {
	log.debug("get lessons by page");
	return new PageImpl<>(studentDaoInt.getByPage(pageable), pageable, studentDaoInt.count());
    }
}
