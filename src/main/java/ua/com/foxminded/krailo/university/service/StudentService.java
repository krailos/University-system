package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.UniversityConfigProperties;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.GroupOverflowException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@Transactional
@Service
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private StudentDao studentDao;
    private GroupDao groupDao;
    private UniversityConfigProperties universityConfigProperties;

    public StudentService(StudentDao studentDao, GroupDao groupDao,
	    UniversityConfigProperties universityConfigData) {
	this.studentDao = studentDao;
	this.groupDao = groupDao;
	this.universityConfigProperties = universityConfigData;
    }

    public Student getById(int id) {
	log.debug("Get student by id={}", id);
	return studentDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Student whith id=%s not exist", id)));
    }

    public List<Student> getAll() {
	log.debug("Get all students");
	return (List<Student>) studentDao.findAll();
    }
    
  public Page<Student> getSelectedPage(Pageable pageable) {
	log.debug("get lessons by page");
	return studentDao.findAll(pageable);
  }

    
    public void create(Student student) {
	log.debug("Create student={}", student);
	checkGroupCapacityNotTooBig(student);
	studentDao.save(student);
    }

    public List<Student> getByGroup(Group group) {
	log.debug("get students  by groupId={}", group.getId());
	return studentDao.getByGroup(group);
    }

    public void delete(Student student) {
	log.debug("Delete student={}", student);
	studentDao.delete(student);
    }

    private void checkGroupCapacityNotTooBig(Student student) {
	Group existingGroup = groupDao.findById(student.getGroup().getId())
		.orElseThrow(() -> new EntityNotFoundException(
			"group for this student not found, groupId=" + student.getGroup().getId()));
	if (existingGroup.getStudents().size() >= universityConfigProperties.getMaxGroupSize()) {
	    throw new GroupOverflowException(
		    "group capacity more then groupMaxSize=" + universityConfigProperties.getMaxGroupSize());
	}
    }

}
