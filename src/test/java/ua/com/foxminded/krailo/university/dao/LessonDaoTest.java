package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Vocation;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewLesson_whenCreate_thenCreated() {
	Lesson lesson = new Lesson.LessonBuilder().
		withDate(LocalDate.of(2000, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(1).built()).
		withSubject(new Subject.SubjectBuilder().withId(1).withName("new subject").built()).
		withAudience(new Audience.AudienceBuilder().withId(1).built()).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		withTimetable(new Timetable.TimetableBuilder().withId(1).built()).built();

	lessonDao.create(lesson);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(7, actual);
    }

    @Test
    void givenNewLessonWithGroups_whenCreate_thenNewRowsInTableLessonsGroupsCreated() {
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2000, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(1).built()).
		withSubject(new Subject.SubjectBuilder().withId(1).withName("new subject").built()).
		withAudience(new Audience.AudienceBuilder().withId(1).built()).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		withTimetable(new Timetable.TimetableBuilder().withId(1).built()).
		withGroups(new ArrayList<>(Arrays.asList(new Group.GroupBuilder().withId(1).built(),new Group.GroupBuilder().withId(2).built()))).
		built();

	lessonDao.create(lesson);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");
	assertEquals(2, actual);
    }

    @Test
    void givenNewFieldsOfLesson_whenUpdate_thenUpdated() {
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2000, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(1).built()).
		withSubject(new Subject.SubjectBuilder().withId(1).withName("new subject").built()).
		withAudience(new Audience.AudienceBuilder().withId(1).built()).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		withTimetable(new Timetable.TimetableBuilder().withId(1).built()).
		built();

	lessonDao.update(lesson);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		"date = '2000-01-01' AND lesson_time_id = 1 AND subject_id = 1 AND teacher_id = 1 AND audience_id = 1 AND timetable_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenNewGroupsOfLesson_whenUpdate_thenUpdated() {
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2000, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(1).built()).
		withSubject(new Subject.SubjectBuilder().withId(1).withName("new subject").built()).
		withAudience(new Audience.AudienceBuilder().withId(1).built()).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		withTimetable(new Timetable.TimetableBuilder().withId(1).built()).
		withGroups(new ArrayList<>(Arrays.asList(new Group.GroupBuilder().withId(1).built(),new Group.GroupBuilder().withId(2).built()))).
		built();
	
	lessonDao.update(lesson);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");
	assertEquals(2, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons", "id = 1");

	int actual = lessonDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenAllGroupsForLessonFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");

	int actual = lessonDao.findById(1).getGroups().size();

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

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(5, actual);
    }

    @Test
    void givenTimetableId_whenFindByTimetableId_thenFound() {

	int actual = lessonDao.findByTimetableId(1).size();

	assertEquals(5, actual);
    }

    @Test
    void givenLesson_whenFindByDate_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons", "date = '2021-01-01'");
	Lesson lesson = new Lesson();
	lesson.setDate(LocalDate.of(2021, 01, 01));

	int actual = lessonDao.findByDate(lesson).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenVocation_whenFindByVocationDate_thenFound() {
	Vocation vocation = new Vocation.VocationBuilder().
		withId(1).
		withKind("kind").
		withApplyingDate(LocalDate.of(2020, 12, 31)).
		withStartDate(LocalDate.of(2021, 01, 01)).
		withEndDate(LocalDate.of(2021, 01, 04)).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		built();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons", "date BETWEEN '2021-01-01' AND '2021-01-04'");

	int actual = lessonDao.findByVocationDate(vocation).size();

	assertEquals(expected, actual);
    }
}
