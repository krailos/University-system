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
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TimetableDaoTest {

    @Autowired
    private TimetableDao timetableDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewTimetable_whenCreate_thenCreated() {
	Timetable timetable = new Timetable("new", new Year(1, "new", null), new Speciality(1, "new", null));

	timetableDao.create(timetable);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfTimetable_whenUpdate_tnenUpdated() {
	Timetable timetable = new Timetable(1, "new", new Year(1, "new", null), new Speciality(1, "new", null));

	timetableDao.update(timetable);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "timetables",
		"name = 'new' AND year_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "timetables", "id =1");

	int actual = timetableDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenTimetables_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");

	int actual = timetableDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	timetableDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables");
	assertEquals(1, actual);
    }

}
