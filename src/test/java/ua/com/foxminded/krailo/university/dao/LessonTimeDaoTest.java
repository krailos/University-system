package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
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

	LessonTime actual = lessonTimeDao.findById(1).get();

	assertEquals(1, actual.getId());
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
	LessonTime lessonTime = LessonTime.builder().startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15))
		.build();

	LessonTime actual = lessonTimeDao.findByStartOrEndLessonTime(lessonTime).get();

	assertTrue(actual.getStartTime().isAfter(LocalTime.of(8, 29)) && actual.getStartTime().isBefore(LocalTime.of(9, 16)));
	assertTrue(actual.getEndTime().isAfter(LocalTime.of(8, 29)) && actual.getEndTime().isBefore(LocalTime.of(9, 16)));
    }

}
