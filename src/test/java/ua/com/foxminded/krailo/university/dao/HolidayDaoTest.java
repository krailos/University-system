package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

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
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfHoliday_whenUpdate_thenUpdated() {
	Holiday holiday = new Holiday(1, "new", LocalDate.of(2001, 01, 01));
	holidayDao.update(holiday);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays",
		"name = 'new' AND date = '2001.01.01'");
	assertEquals(expected, actual);
    }

}
