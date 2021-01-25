package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Department;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewTeacher_whenCreate_thenCreated() {
	Teacher teacher = new Teacher("new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));
	teacherDao.create(teacher);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher teacher = new Teacher(1, "new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));
	teacherDao.update(teacher);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers",
		"teacher_id = 'new' AND first_name  = 'new' AND last_name = 'new' AND birth_date = '2000-01-01' AND phone = 'new' AND address = 'new' AND email = 'new'AND degree = 'new'  AND gender = 'MALE' AND id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "id = 1");
	int actual = teacherDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	int actual = teacherDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	teacherDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(expected, actual);
    }

}
