package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewHoliday_whenCreate_thenCreated() {
	Lesson lesson = new Lesson(LocalDate.of(2000, 01, 01), new LessonTime(1, null, null,null,null), new Subject(1, null), 
		 new Audience(1, null, null, 0, null), new Teacher(1, null, null,null,null, null, null, null,null,null, null), new Timetable(1, null, null,null));
	lessonDao.create(lesson);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfHoliday_whenUpdate_thenUpdated() {
	Holiday holiday = new Holiday(1, "new", LocalDate.of(2001, 01, 01));
	lessonDao.update(holiday);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays",
		"name = 'new' AND date = '2001-01-01'");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays", "id = 1");
	int actual = lessonDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenHolidays_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	int actual = lessonDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {
	lessonDao.deleteById(2);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "holidays");
	assertEquals(expected, actual);
    }

}
