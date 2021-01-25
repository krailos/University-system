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
    void givenNewLesson_whenCreate_thenCreated() {
	Lesson lesson = new Lesson(LocalDate.of(2000, 01, 01), new LessonTime(1), new Subject(1, null), 
		 new Audience(1), new Teacher(1), new Timetable(1));
	lessonDao.create(lesson);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfLesson_whenUpdate_thenUpdated() {
	Lesson lesson = new Lesson(1, LocalDate.of(2000, 01, 01), new LessonTime(1), new Subject(1, null), 
		 new Audience(1), new Teacher(1), new Timetable(1));
	lessonDao.update(lesson);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		"date = '2000-01-01' AND lesson_time_id = 1 AND subject_id = 1 AND teacher_id = 1 AND audience_id = 1 AND timetable_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "holidays", "id = 1");
	int actual = lessonDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenLessons_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	int actual = lessonDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {
	lessonDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(expected, actual);
    }
    
    @Test
    void givenLesson_whenFindGroupsByLessonId_thenFound() {
	Lesson lesson = new Lesson(1, LocalDate.of(2000, 01, 01), new LessonTime(1), new Subject(1, null), 
		 new Audience(1), new Teacher(1), new Timetable(1));
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");
	int actual = lessonDao.findGroupsByLessonId(lesson).size();
	assertEquals(expected, actual);
    }
}
