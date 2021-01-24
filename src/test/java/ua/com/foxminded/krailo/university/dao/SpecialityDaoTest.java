package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class SpecialityDaoTest {

    @Autowired
    private SpecialityDao specialityDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewSpeciality_whenCreate_thenCreated() {
	Speciality speciality = new Speciality("new", new Faculty(1, "new", null));
	specialityDao.create(speciality);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfFaculty_whenUpdate_tnenUpdated() {
	Speciality speciality = new Speciality(1, "new", new Faculty(1, "new", null));
	specialityDao.update(speciality);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities",
		"name = 'new' AND faculty_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities", "id =1");
	int actual = specialityDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialities_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	int actual = specialityDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	specialityDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	assertEquals(expected, actual);
    }

}
