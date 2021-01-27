package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Department;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewTeacher_whenCreate_thenCreated() {
	Teacher teacher = new Teacher("new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));

	teacherDao.create(teacher);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(3, actual);
    }
    
    @Test
    void givenNewTeacherWithSubjects_whenCreate_thenNewRowsInTableTeachersSubjectsCreated() {
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject(1, ""), new Subject(2,"")));
	Teacher teacher = new Teacher("new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));
	teacher.setSubjects(subjects);

	teacherDao.create(teacher);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers_subjects", "teacher_id = 3");
	assertEquals(expected, actual);
    }


    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher teacher = new Teacher(1, "new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));

	teacherDao.update(teacher);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers",
		"teacher_id = 'new' AND first_name  = 'new' AND last_name = 'new' AND birth_date = '2000-01-01' AND phone = 'new' AND address = 'new' AND email = 'new'AND degree = 'new'  AND gender = 'MALE' AND id = 1");
	assertEquals(1, actual);
    }
    
    @Test
    void givenNewSubjectsOfTeacher_whenUpdate_thenUpdated() {
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject(2,"")));
	Teacher teacher = new Teacher(1, "new", "new", "new", LocalDate.of(2000, 01, 01), "new", "new", "new", "new",
		Gender.MALE, new Department(1, "new"));
	teacher.setSubjects(subjects);

	teacherDao.update(teacher);

	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers_subjects", "teacher_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "id = 1");

	int actual = teacherDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");

	int actual = teacherDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	teacherDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(1, actual);
    }

    @Test
    void givenSubjectId_whenFindTeachersBySubjectId_thenFound() {
	Subject subject = new Subject(1, "");
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "id = 1 OR id = 2");

	int actual = teacherDao.findTeacherBySubjectId(subject.getId()).size();

	assertEquals(expected, actual);
    }
    
}
