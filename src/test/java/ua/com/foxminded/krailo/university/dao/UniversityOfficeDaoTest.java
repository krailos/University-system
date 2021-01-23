package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.UniversityOffice;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class UniversityOfficeDaoTest {

    @Autowired
    private UniversityOfficeDao universityOfficeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "university_office ", "id = 1");
	int actual = universityOfficeDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenUniversityOffices_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");
	int actual = universityOfficeDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	universityOfficeDao.deleteById(2);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfUniversity_office_whenUpdate_thenUpdated() {
	UniversityOffice universityOffice = new UniversityOffice(1, "new", "new");
	universityOfficeDao.update(universityOffice);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "university_office",
		"name = 'new' AND address = 'new'");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewUniversity_office_whenCreate_thenCreated() {
	UniversityOffice universityOffice = new UniversityOffice("new", "new");
	universityOfficeDao.create(universityOffice);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");
	assertEquals(expected, actual);
    }
}
