package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Year;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class YearDaoTest {

    @Autowired
    private YearDao yearDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewYear_whenCreate_thenCreated() {
	Year year = new Year("new", new Speciality(1, "new", null));

	yearDao.create(year);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfYear_whenUpdate_tnenUpdated() {
	Year year = new Year(1, "new", new Speciality(1, "new", null));

	yearDao.update(year);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years", "name = 'new' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years", "id =1");

	int actual = yearDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenYears_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");

	int actual = yearDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	yearDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");
	assertEquals(1, actual);
    }

}
