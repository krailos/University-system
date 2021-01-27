package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonTimeDaoTest {

    @Autowired
    private LessonTimeDao lessonTimeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewLessonTime_whenCreate_thenCreated() {
	LessonTime lessonTime = new LessonTime("new", LocalTime.of(12, 00), LocalTime.of(12, 45),
		new LessonsTimeSchedule(1, "new"));

	lessonTimeDao.create(lessonTime);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfLossonTime_whenUpdate_tnenUpdated() {
	LessonTime lessonTime = new LessonTime(1, "new", LocalTime.of(12, 00), LocalTime.of(12, 45),
		new LessonsTimeSchedule(1, "new"));

	lessonTimeDao.update(lessonTime);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times",
		"order_number = 'new' AND lessons_timeschedule_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times", "id =1");

	int actual = lessonTimeDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");

	int actual = lessonTimeDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	lessonTimeDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");
	assertEquals(1, actual);
    }

}
