package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LesonsTimescheduleDaoTest {

    @Autowired
    private LessonTimeScheduleDao lessonsTimesceduleDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewLessonsTimeSchedule_whenCreate_thenCreated() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder()
		.withName("new lesson time schedule").built();

	lessonsTimesceduleDao.create(lessonsTimeSchedule);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_timeschedule");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfLessonsTimeSchedule_whenUpdate_tnenUpdated() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder().withId(1)
		.withName("new lesson time schedule").built();

	lessonsTimesceduleDao.update(lessonsTimeSchedule);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_timeschedule", "name = 'new lesson time schedule'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_timeschedule", "id =1");

	int actual = lessonsTimesceduleDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsTimeSchedules_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_timeschedule");

	int actual = lessonsTimesceduleDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	lessonsTimesceduleDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_timeschedule");
	assertEquals(1, actual);
    }

}
