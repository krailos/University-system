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
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.model.Department;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewTeacher_whenCreate_thenCreated() {
	Teacher teacher = Teacher.builder().teacherId("teacher id").firstName("first name").lastName("last name")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address").phone("phone").email("email").degree("degree")
		.department(Department.builder().id(1).name("new pepartment").build()).gender(Gender.MALE).build();

	teacherDao.create(teacher);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(3, actual);
    }

    @Test
    void givenNewTeacherWithSubjects_whenCreate_thenNewRowsInTableTeachersSubjectsCreated() {
	Teacher teacher = Teacher.builder().id(3).teacherId("teacher id").firstName("first name").lastName("last name")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address").phone("phone").email("email").degree("degree")
		.department(Department.builder().id(1).name("new pepartment").build()).gender(Gender.MALE).build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build()));
	teacher.setSubjects(subjects);

	teacherDao.create(teacher);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers_subjects", "teacher_id = 3");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher teacher = Teacher.builder().id(1).teacherId("teacher id").firstName("first name").lastName("last name")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address").phone("phone").email("email").degree("degree")
		.department(Department.builder().id(1).name("new pepartment").build()).gender(Gender.MALE).build();

	teacherDao.update(teacher);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers",
		"teacher_id = 'teacher id' AND first_name  = 'first name' AND last_name = 'last name' AND birth_date = '2000-01-01'"
			+ " AND phone = 'phone' AND address = 'address' AND email = 'email'AND degree = 'degree'  AND gender = 'MALE' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenNewSubjectsOfTeacher_whenUpdate_thenUpdated() {
	Teacher teacher = Teacher.builder().id(1).teacherId("teacher id").firstName("first name").lastName("last name")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address").phone("phone").email("email").degree("degree")
		.department(Department.builder().id(1).name("new pepartment").build()).gender(Gender.MALE).build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build()));
	teacher.setSubjects(subjects);

	teacherDao.update(teacher);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers_subjects", "teacher_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	
	Teacher actual = teacherDao.findById(1).get();

	assertEquals(1, actual.getId());
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
    void givenSubjectId_whenFindBySubjectId_thenFound() {
	Subject subject = Subject.builder().id(1).name("subject1").build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "id = 1 OR id = 2");

	int actual = teacherDao.findBySubjectId(subject.getId()).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenDepertmentId_whenFindByDepartmentId_thenFound() {
	Department department = Department.builder().id(1).name("name department").build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "department_id = 1");

	int actual = teacherDao.findByDepartmentId(department.getId()).size();

	assertEquals(expected, actual);
    }

}
