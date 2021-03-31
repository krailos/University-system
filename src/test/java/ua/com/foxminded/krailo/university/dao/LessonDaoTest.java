package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
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

	Lesson actual = lessonDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenId_whenFindById_thenAllGroupsForLessonFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");

	int actual = lessonDao.findById(1).get().getGroups().size();

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

	int actual = lessonDao.findByDate(lesson.getDate()).size();

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

	int actual = lessonDao.findByTeacherAndDate(teacher, LocalDate.of(2021, 01, 01)).size();

	assertEquals(expected, actual);
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

	int actual = lessonDao.findByStudentAndDate(student, LocalDate.of(2021, 01, 01)).size();

	assertEquals(2, actual);
    }

    @Test
    void givenId_whenFindByDateByTeacherByLessonTime_thenFound() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2021, 01, 01)).teacher(Teacher.builder().id(1).build())
		.lessonTime(LessonTime.builder().id(1).build()).build();

	Lesson actual = lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId()).get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getTeacher().getId(), actual.getTeacher().getId());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
    }

    @Test
    void givenLesson_whenFindByDateAndAudienceAndLessonTime_thenFound() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2021, 01, 01)).audience(Audience.builder().id(1).build())
		.lessonTime(LessonTime.builder().id(1).build()).build();

	Lesson actual = lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId()).get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getAudience().getId(), actual.getAudience().getId());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
    }

    @Test
    void givenDateLessonTimeIdGroupId_whenFindByDateAndLessonTimeAndGroup_thenFound() {
	Lesson lesson = Lesson.builder().date(LocalDate.of(2021, 01, 01)).audience(Audience.builder().id(1).build())
		.lessonTime(LessonTime.builder().id(1).build()).build();
	lesson.getGroups().add(Group.builder().id(1).build());

	Lesson actual = lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId()).get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
	assertEquals(lesson.getGroups().get(0).getId(), actual.getGroups().get(0).getId());
    }

}
