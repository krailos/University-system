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
	Holiday holiday = Holiday.builder().name("Holiday 3").date(LocalDate.of(2021, 01, 03)).build();

	holidayDao.create(holiday);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfHoliday_whenUpdate_thenUpdated() {
	Holiday holiday = Holiday.builder().id(1).name("new Holiday").date(LocalDate.of(2021, 01, 03)).build();

	holidayDao.update(holiday);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays",
		"name = 'new Holiday' AND date = '2021-01-03'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Holiday actual = holidayDao.findById(1).get();

	assertEquals(1, actual.getId());
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

    @Test
    void givenId_whenFindByDate_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays", "date = '2021-01-01'");

	int actual = holidayDao.findById(1).get().getId();

	assertEquals(expected, actual);
    }

}
