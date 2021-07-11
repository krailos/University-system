package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HibernateStudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewStudent_whenCreate_thenCreated() {
	Student expected = getStudent();
	expected.setId(0);

	studentDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Student.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfStudents_whenUpdate_tnenUpdated() {
	Student expected = getStudent();
	expected.setFirstName("new first name");
	expected.setGroup(Group.builder().id(2).name("group 2").build());

	studentDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Student.class, expected.getId()));
    }

    @Test
    void givenId_whenFindById_thenFound() {
	Student expected = getStudent();

	Student actual = studentDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenStudents_getAll_thenGot() {
	List<Student> expected = new ArrayList<>();
	expected.add(Student.builder().id(1).studentId("1id").firstName("student first name 1")
		.lastName("student last name 1").birthDate(LocalDate.of(2000, 01, 01)).address("address 1")
		.phone("0670000001").email("email 1").rank("0").gender(Gender.MALE)
		.group(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build())
		.build());
	expected.add(Student.builder().id(2).studentId("2id").firstName("student first name 2")
		.lastName("student last name 2").birthDate(LocalDate.of(2002, 02, 02)).address("address 2")
		.phone("0670000002").email("email 2").rank("0").gender(Gender.FEMALE)
		.group(Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build())
		.build());

	List<Student> actual = studentDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenGroup_whenGetByGroup_thenGot() {
	List<Student> expected = new ArrayList<>();
	expected.add(Student.builder().id(1).studentId("1id").firstName("student first name 1")
		.lastName("student last name 1").birthDate(LocalDate.of(2000, 01, 01)).address("address 1")
		.phone("0670000001").email("email 1").rank("0").gender(Gender.MALE)
		.group(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build())
		.build());

	List<Student> students = studentDao.getByGroup(Group.builder().id(1).name("group 1").build());

	assertEquals(expected, students);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Student student = getStudent();

	studentDao.delete(student);

	assertNull(hibernateTemplate.get(Student.class, student.getId()));
    }

    @Test
    void givenStudents_whenCount_thenCounted() {

	int actual = studentDao.count();

	assertEquals(hibernateTemplate.execute( session -> session.createQuery("from Student").list().size()), actual);
    }

    @Test
    void givnStudents_whenFindWithLimit_thenGot() {
	int PageNo = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(PageNo, pageSize);
	List<Student> students = new ArrayList<>();
	students.add(Student.builder().id(1).studentId("1id").firstName("student first name 1")
		.lastName("student last name 1").birthDate(LocalDate.of(2000, 01, 01)).address("address 1")
		.phone("0670000001").email("email 1").rank("0").gender(Gender.MALE)
		.group(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build())
		.build());
	students.add(Student.builder().id(2).studentId("2id").firstName("student first name 2")
		.lastName("student last name 2").birthDate(LocalDate.of(2002, 02, 02)).address("address 2")
		.phone("0670000002").email("email 2").rank("0").gender(Gender.FEMALE)
		.group(Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build())
		.build());

	Page<Student> expected = new PageImpl<>(students, pageable, 2);

	Page<Student> actual = studentDao.getAll(pageable);

	assertEquals(expected, actual);
    }

    private Student getStudent() {
	return Student.builder().id(1).studentId("1id").firstName("student first name 1")
		.lastName("student last name 1").birthDate(LocalDate.of(2000, 01, 01)).address("address 1")
		.phone("0670000001").email("email 1").rank("0").gender(Gender.MALE)
		.group(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build())
		.build();
    }

}
