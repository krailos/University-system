package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.UniversityOffice;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class DeansOfficeTest {

    @Autowired
    private DeansOfficeDao deansOfficeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewDeansOffice_whenCreate_thenCreated() {
	DeansOffice deansOffice = new DeansOffice("new", new UniversityOffice(1, "new", "new"));
	deansOfficeDao.create(deansOffice);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfDeansOffice_whenUpdate_tnenUpdated() {
	DeansOffice deansOffice = new DeansOffice(1, "new", new UniversityOffice(1, "new", "new"));
	deansOfficeDao.update(deansOffice);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "deans_office",
		"name = 'new' AND university_office_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "deans_office", "id =1");
	int actual = deansOfficeDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenDeansOffices_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");
	int actual = deansOfficeDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	deansOfficeDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");
	assertEquals(expected, actual);
    }

}
