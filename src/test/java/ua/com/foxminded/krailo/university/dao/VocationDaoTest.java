package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class VocationDaoTest {

    @Autowired
    private VocationDao vocationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewVocation_whenCreate_thenCreated() {
	Vocation vocation = new Vocation("new", LocalDate.of(2000, 01, 01), LocalDate.of(2000, 02, 02), LocalDate.of(2000, 03, 03), new Teacher(1));
	vocationDao.create(vocation);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfVocation_whenUpdate_thenUpdated() {
	Vocation vocation = new Vocation(1, "new", LocalDate.of(2000, 01, 01), LocalDate.of(2000, 02, 02), LocalDate.of(2000, 03, 03), new Teacher(1));
	vocationDao.update(vocation);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations",
		"kind = 'new' AND applying_date = '2000-01-01' AND start_date = '2000-02-02' AND end_date = '2000-03-03'AND teacher_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations", "id = 1");
	int actual = vocationDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenVocations_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	int actual = vocationDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {
	vocationDao.deleteById(2);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	assertEquals(expected, actual);
    }

}
