package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.StudentDaoInt;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class StudentDaoHobernateTest {

    @Autowired
    private StudentDaoInt studentDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewStudent_whenCreate_thenCreated() {
	Student student = getStudent();
	student.setId(0);

	studentDao.create(student);

	assertEquals(student, hibernateTemplate.get(Student.class, 3));
    }

    @Test
    void givenNewFieldsOfStudents_whenUpdate_tnenUpdated() {
	Student student = getStudent();
	student.setFirstName("new first name");
	student.setGroup(Group.builder().id(2).name("group 2").build());

	studentDao.update(student);

	assertEquals(student.getFirstName(), hibernateTemplate.get(Student.class, 1).getFirstName());
	assertEquals(student.getGroup().getId(), hibernateTemplate.get(Student.class, 1).getGroup().getId());
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Student actual = studentDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenStudents_getAll_thenGot() {

	int actual = studentDao.getAll().size();

	assertEquals(2, actual);
    }

    @Test
    void givenGroup_whenGetByGroup_thenGot() {
	Student student = getStudent();

	List<Student> students = studentDao.getByGroup(student.getGroup());

	assertEquals(1, students.size());
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Student student = getStudent();

	studentDao.delete(student);

	assertEquals(null, hibernateTemplate.get(Student.class, 1));
    }

    @Test
    void givenStudents_whenCount_thenCounted() {

	int actual = studentDao.count();

	assertEquals(2, actual);
    }

    @Test
    void givnStudents_whenFindWithLimit_thenGot() {
	int PageNo = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(PageNo, pageSize);

	List<Student> students = studentDao.getByPage(pageable);

	assertEquals(2, students.size());
    }

    private Student getStudent() {
	return Student.builder().id(1).studentId("1id").firstName("student first name 1")
		.lastName("student last name 1").birthDate(LocalDate.of(2000, 01, 01)).address("address 1")
		.phone("0670000001").email("email 1").rank("0").gender(Gender.MALE)
		.group(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build())
		.build();

    }

}
