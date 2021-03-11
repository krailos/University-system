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
	Group group = Group.builder().name("new name")
		.year(Year.builder().id(1).name("new name").build()).build();

	groupDao.create(group);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfGroup_whenUpdate_tnenUpdated() {
	Group group = Group.builder().id(1).name("new name")
		.year(Year.builder().id(1).name("new name").build()).build();


	groupDao.update(group);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "name = 'new name' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Group actual = groupDao.findById(1).get();

	assertEquals(1, actual.getId());
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
	Lesson lesson = Lesson.builder().id(1).date(LocalDate.of(2000, 01, 01))
		.lessonTime(LessonTime.builder().id(1).build())
		.subject(Subject.builder().id(1).name("new name").build())
		.audience(Audience.builder().id(1).build())
		.teacher(Teacher.builder().id(1).build()).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "lessons_groups", "lesson_id = 1");

	int actual = groupDao.findByLessonId(lesson.getId()).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenYearId_whenFindByYearId_thenFound() {
	Year year = Year.builder().id(1).name("new name").build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "year_id = 1");

	int actual = groupDao.findByYearId(year.getId()).size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenGroupName_whenFindByName_thenFound() {
	
	Group actual = groupDao.findByNameAndYearId("group 1", 1).get();

	assertEquals("group 1", actual.getName());
    }

}
