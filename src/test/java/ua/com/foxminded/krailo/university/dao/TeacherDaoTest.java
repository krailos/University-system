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
	Teacher teacher = new Teacher.TeacherBuilder().withTeacherId("teacher id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withDegree("degree").
		withDepartment(new Department.DepartmentBuilder().withId(1).withName("new pepartment").built()).
		withGender(Gender.MALE).
		built();

	teacherDao.create(teacher);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
	assertEquals(3, actual);
    }

    @Test
    void givenNewTeacherWithSubjects_whenCreate_thenNewRowsInTableTeachersSubjectsCreated() {
	Teacher teacher = new Teacher.TeacherBuilder().
		withId(3).
		withTeacherId("teacher id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withDegree("degree").
		withDepartment(new Department.DepartmentBuilder().withId(1).withName("new pepartment").built()).
		withGender(Gender.MALE).
		built();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject.SubjectBuilder().withId(1).withName("subject1").built(),
		new Subject.SubjectBuilder().withId(2).withName("subject2").built()));
	teacher.setSubjects(subjects);

	teacherDao.create(teacher);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers_subjects", "teacher_id = 3");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher teacher = new Teacher.TeacherBuilder().
		withId(1).
		withTeacherId("teacher id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withDegree("degree").
		withDepartment(new Department.DepartmentBuilder().withId(1).withName("new pepartment").built()).
		withGender(Gender.MALE).
		built();

	teacherDao.update(teacher);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers",
		"teacher_id = 'teacher id' AND first_name  = 'first name' AND last_name = 'last name' AND birth_date = '2000-01-01'"
		+ " AND phone = 'phone' AND address = 'address' AND email = 'email'AND degree = 'degree'  AND gender = 'MALE' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenNewSubjectsOfTeacher_whenUpdate_thenUpdated() {
	Teacher teacher = new Teacher.TeacherBuilder().
		withId(1).
		withTeacherId("teacher id").
		withFirstName("first name").
		withlastName("last name").
		withBirthDate(LocalDate.of(2000, 01, 01)).
		withAddress("address").
		withPhone("phone").
		withEmail("email").
		withDegree("degree").
		withDepartment(new Department.DepartmentBuilder().withId(1).withName("new pepartment").built()).
		withGender(Gender.MALE).
		built();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject.SubjectBuilder().withId(1).withName("subject1").built(),
		new Subject.SubjectBuilder().withId(2).withName("subject2").built()));
	teacher.setSubjects(subjects);

	teacherDao.update(teacher);

	int expected = 2;
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
    void givenSubjectId_whenFindBySubjectId_thenFound() {
	Subject subject = new Subject.SubjectBuilder().withId(1).withName("subject1").built();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "id = 1 OR id = 2");

	int actual = teacherDao.findBySubjectId(subject.getId()).size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenDepertmentId_whenFindByDepartmentId_thenFound() {
	Department department = new Department.DepartmentBuilder().withId(1).withName("name department").built();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "teachers", "department_id = 1");

	int actual = teacherDao.findByDepartmentId(department.getId()).size();

	assertEquals(expected, actual);
    }

}
