package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class FacultyDaoTest {

    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewFaculty_whenCreate_thenCreated() {
	Faculty faculty = new Faculty.FacultyBuilder().withName("new name").withDeansOffice(
		new DeansOffice.DeansOfficeBuilder().withId(1).withName("new name").withUniversityOffice(null).built())
		.built();

	facultyDao.create(faculty);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfFaculty_whenUpdate_tnenUpdated() {
	Faculty faculty = new Faculty.FacultyBuilder().withId(1).withName("new name").withDeansOffice(
		new DeansOffice.DeansOfficeBuilder().withId(1).withName("new name").withUniversityOffice(null).built())
		.built();

	facultyDao.update(faculty);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties",
		"name = 'new name' AND deans_office_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "faculties", "id =1");

	int actual = facultyDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenFaculties_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");

	int actual = facultyDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	facultyDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "faculties");
	assertEquals(1, actual);
    }

}
