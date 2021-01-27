package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Holiday;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HolidayDaoTest {

    @Autowired
    private HolidayDao holidayDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewHoliday_whenCreate_thenCreated() {
	Holiday holiday = new Holiday("Holiday 3", LocalDate.of(2021, 01, 03));

	holidayDao.create(holiday);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfHoliday_whenUpdate_thenUpdated() {
	Holiday holiday = new Holiday(1, "new", LocalDate.of(2001, 01, 01));

	holidayDao.update(holiday);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays",
		"name = 'new' AND date = '2001-01-01'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays", "id = 1");

	int actual = holidayDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenHolidays_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");

	int actual = holidayDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {

	holidayDao.deleteById(2);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	assertEquals(1, actual);
    }

}
