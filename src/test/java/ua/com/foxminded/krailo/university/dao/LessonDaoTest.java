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
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonDaoTest {

    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewLesson_whenCreate_thenCreated() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2000, 01, 01)).lessonTime(LessonTime.builder().id(1).build())
		.subject(Subject.builder().id(1).name("new subject").build()).audience(Audience.builder().id(1).build())
		.teacher(Teacher.builder().id(1).build()).build();

	lessonDao.create(lesson);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");
	assertEquals(7, actual);
    }

    @Test
    void givenNewLessonWithGroups_whenCreate_thenNewRowsInTableLessonsGroupsCreated() {
	Lesson lesson = Lesson.builder().id(1).date(LocalDate.of(2000, 01, 01))
		.lessonTime(LessonTime.builder().id(1).build())
		.subject(Subject.builder().id(1).name("new subject").build()).audience(Audience.builder().id(1).build())
		.teacher(Teacher.builder().id(1).build())
		.groups(new ArrayList<>(Arrays.asList(Group.builder().id(1).build(), Group.builder().id(2).build())))
		.build();

	lessonDao.create(lesson);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");
	assertEquals(2, actual);
    }

    @Test
    void givenNewFieldsOfLesson_whenUpdate_thenUpdated() {
	Lesson lesson = Lesson.builder().id(1).date(LocalDate.of(2000, 01, 01))
		.lessonTime(LessonTime.builder().id(1).build())
		.subject(Subject.builder().id(1).name("new subject").build()).audience(Audience.builder().id(1).build())
		.teacher(Teacher.builder().id(1).build()).build();

	lessonDao.update(lesson);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		"date = '2000-01-01' AND lesson_time_id = 1 AND subject_id = 1 AND teacher_id = 1 AND audience_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenNewGroupsOfLesson_whenUpdate_thenUpdated() {
	Lesson lesson = Lesson.builder().id(1).date(LocalDate.of(2000, 01, 01))
		.lessonTime(LessonTime.builder().id(1).build())
		.subject(Subject.builder().id(1).name("new subject").build()).audience(Audience.builder().id(1).build())
		.teacher(Teacher.builder().id(1).build())
		.groups(new ArrayList<>(Arrays.asList(Group.builder().id(1).build(), Group.builder().id(2).build())))
		.build();

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
    void givenLesson_whenFindByDate_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons", "date = '2021-01-01'");
	Lesson lesson = new Lesson();
	lesson.setDate(LocalDate.of(2021, 01, 01));

	int actual = lessonDao.findByDate(lesson).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacherAndDate_whenFindByTeacherByMonth_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		" teacher_id = 1 and date BETWEEN '2021-01-01' AND '2021-02-01'");

	int actual = lessonDao.findByTeacherByMonth(teacher, LocalDate.of(2021, 01, 01)).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacherAndDate_whenFindByTeacherBetweenDates_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		" teacher_id = 1 and date BETWEEN '2021-01-01' AND '2021-01-05'");

	int actual = lessonDao
		.findByTeacherBetweenDates(teacher, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05)).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacherAndDate_whenFindByTeacherByDate_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		"teacher_id = 1 AND date = '2021-01-01'");

	int actual = lessonDao.findByTeacherByDate(teacher, LocalDate.of(2021, 01, 01)).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenStudentAndDate_whenFindByStudentByMonth_thenFound() {
	Student student = Student.builder().id(1).build();

	int actual = lessonDao.findByStudentByMonth(student, LocalDate.of(2021, 01, 01)).size();

	assertEquals(2, actual);
    }

    @Test
    void givenStudentAndDate_whenFindByStudentBetweenDates_thenFound() {
	Student student = Student.builder().id(1).build();

	int actual = lessonDao
		.findByStudentBetweenDates(student, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05)).size();

	assertEquals(2, actual);
    }

    @Test
    void givenStudentAndDate_whenFindByStudentByDate_thenFound() {
	Student student = Student.builder().id(1).build();

	int actual = lessonDao.findByStudentByDate(student, LocalDate.of(2021, 01, 01)).size();

	assertEquals(2, actual);
    }

    @Test
    void givenId_whenFindByDateByTeacherByLessonTime_thenFound() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2021, 01, 01)).teacher(Teacher.builder().id(1).build())
		.lessonTime(LessonTime.builder().id(1).build()).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		" date = '2021-001-01' AND  teacher_id = 1 AND lesson_time_id = 1");

	int actual = lessonDao.findByDateByTeacherByLessonTime(lesson).getId();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenId_whenFindByDateByAudienceByLessonTime_thenFound() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2021, 01, 01)).audience(Audience.builder().id(1).build())
		.lessonTime(LessonTime.builder().id(1).build()).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons",
		" date = '2021-001-01' AND  audience_id = 1 AND lesson_time_id = 1");

	int actual = lessonDao.findByDateByAudienceByLessonTime(lesson).getId();

	assertEquals(expected, actual);
    }

}
