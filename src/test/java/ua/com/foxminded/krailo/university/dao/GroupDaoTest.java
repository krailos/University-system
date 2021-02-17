package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
import ua.com.foxminded.krailo.university.model.Year;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewGroup_whenCreate_thenCreated() {
	Group group = new Group.GroupBuilder().withName("new name")
		.withYear(new Year.YearBuilder().withId(1).withName("new name").withSpeciality(null).built()).built();

	groupDao.create(group);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfGroup_whenUpdate_tnenUpdated() {
	Group group = new Group.GroupBuilder().withId(1).withName("new name")
		.withYear(new Year.YearBuilder().withId(1).withName("new name").withSpeciality(null).built()).built();

	groupDao.update(group);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "name = 'new name' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "id = 1");

	int actual = groupDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");

	int actual = groupDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	groupDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	assertEquals(1, actual);
    }

    @Test
    void givenLessonId_whenFindGroupsByLessonId_thenFound() {
	Lesson lesson = new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2000, 01, 01))
		.withLessonTime(new LessonTime.LessonTimeBuilder().withId(1).built())
		.withSubject(new Subject.SubjectBuilder().withId(1).withName("new name").built())
		.withAudience(new Audience.AudienceBuilder().withId(1).built())
		.withTeacher(new Teacher.TeacherBuilder().withId(1).built())
		.withTimetable(new Timetable.TimetableBuilder().withId(1).built()).built();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");

	int actual = groupDao.findByLessonId(lesson.getId()).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenYearId_whenFindByYearId_thenFound() {
	Year year = new Year.YearBuilder().withId(1).withName("new name").withSpeciality(null).built();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "year_id = 1");

	int actual = groupDao.findByYearId(year.getId()).size();

	assertEquals(expected, actual);
    }

}
