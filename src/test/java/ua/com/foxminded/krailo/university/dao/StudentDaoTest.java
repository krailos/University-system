package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewStudent_whenCreate_thenCreated() {
	Student student = new Student.StudentBuilder().
		withStudentId("student id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withRank("rank").
		withGender(Gender.MALE).
		withGroup(new Group.GroupBuilder().withId(1).withName("group name").built()).
		built();

	studentDao.create(student);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfStudents_whenUpdate_tnenUpdated() {
	Student student = new Student.StudentBuilder().
		withId(1).
		withStudentId("student id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withRank("rank").
		withGender(Gender.MALE).
		withGroup(new Group.GroupBuilder().withId(1).withName("group name").built()).
		built();

	studentDao.update(student);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students",
		"student_id = 'student id' AND first_name  = 'first name' AND last_name = 'last name' AND birth_date = '2000-01-01'"
		+ " AND phone = 'phone' AND address = 'address' AND email = 'email' AND gender = 'MALE' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students", "id = 1");

	int actual = studentDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenStudents_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");

	int actual = studentDao.findAll().size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenGroupId_whenFindByGroupId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students", "group_id = 1");

	int actual = studentDao.findByGroupId(1).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	studentDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
	assertEquals(1, actual);
    }

}
