package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class FacultyDaoTest {

    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewFaculty_whenCreate_thenCreated() {
	Faculty faculty = new Faculty("new", new DeansOffice(1, "new", null));
	facultyDao.create(faculty);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfFaculty_whenUpdate_tnenUpdated() {
	Faculty faculty = new Faculty(1, "new", new DeansOffice(1, "new", null));
	facultyDao.update(faculty);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties",
		"name = 'new' AND deans_office_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties", "id =1");
	int actual = facultyDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenDeansOffices_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
	int actual = facultyDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	facultyDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
	assertEquals(expected, actual);
    }

}
