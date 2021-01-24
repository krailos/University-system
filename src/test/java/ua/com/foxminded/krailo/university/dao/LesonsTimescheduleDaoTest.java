package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LesonsTimescheduleDaoTest {

    @Autowired
    private LessonsTimescheduleDao lessonsTimesceduleDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewLessonsTimeSchedule_whenCreate_thenCreated() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule("new");
	lessonsTimesceduleDao.create(lessonsTimeSchedule);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_timeschedule");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfLessonsTimeSchedule_whenUpdate_tnenUpdated() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "new");
	lessonsTimesceduleDao.update(lessonsTimeSchedule);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_timeschedule", "name = 'new'");
	assertEquals(expected, actual);
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
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons_timeschedule");
	assertEquals(expected, actual);
    }

}
