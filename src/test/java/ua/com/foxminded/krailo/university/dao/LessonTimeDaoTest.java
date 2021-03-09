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
	LessonTime lessonTime = LessonTime.builder().orderNumber("new order number").startTime(LocalTime.of(12, 00))
		.endTime(LocalTime.of(12, 45))
		.lessonsTimeSchedule(LessonsTimeSchedule.builder().id(1).name("new name").build()).build();

	lessonTimeDao.create(lessonTime);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfLossonTime_whenUpdate_tnenUpdated() {
	LessonTime lessonTime = LessonTime.builder().orderNumber("new order number").id(1)
		.startTime(LocalTime.of(12, 00)).endTime(LocalTime.of(12, 45))
		.lessonsTimeSchedule(LessonsTimeSchedule.builder().id(1).name("new name").build()).build();
	lessonTimeDao.update(lessonTime);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times",
		"order_number = 'new order number' AND lessons_timeschedule_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times", "id =1");

	int actual = lessonTimeDao.findById(1).get().getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");

	int actual = lessonTimeDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimeScheduleId_whenFindByLessonsTimeScheduleId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times", "lessons_timeschedule_id = 1");

	int actual = lessonTimeDao.findBylessonTimeScheduleId(1).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	lessonTimeDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_times");
	assertEquals(1, actual);
    }

    @Test
    void givenLessonTime_whenFindByStartEndLessonTime_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lesson_times",
		"'08:46:00' BETWEEN start_time AND end_time " + " OR '09:25:00' BETWEEN start_time AND end_time");

	LessonTime lessonTime = LessonTime.builder().startTime(LocalTime.of(8, 46)).endTime(LocalTime.of(9, 25))
		.build();

	int actual = lessonTimeDao.findByStartOrEndLessonTime(lessonTime).get().getId();

	assertEquals(expected, actual);
    }

}
