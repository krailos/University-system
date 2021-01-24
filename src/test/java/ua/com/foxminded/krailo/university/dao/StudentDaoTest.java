package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewStudent_whenCreate_thenCreated() {
	Student student = new Student("new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Group(1, "new", null));
	studentDao.create(student);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfStudents_whenUpdate_tnenUpdated() {
	Student student = new Student(1, "new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Group(1, "new", null));
	studentDao.update(student);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "students",
		"student_id = 'new' AND first_name  = 'new' AND last_name = 'new' AND birth_date = '2000-01-01' AND phone = 'new' AND address = 'new' AND email = 'new' AND gender = 'male' AND id = 1");
	assertEquals(expected, actual);
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
    void givenId_whenDeleteById_thenDeleted() {
	studentDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
	assertEquals(expected, actual);
    }

}
