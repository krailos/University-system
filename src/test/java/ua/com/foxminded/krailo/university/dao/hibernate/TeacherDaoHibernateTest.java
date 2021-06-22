package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.TeacherDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TeacherDaoHibernateTest {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewTeacher_whenCreate_thenCreated() {
	Teacher expected = getTeacher();
	expected.setId(0);

	teacherDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Teacher.class, expected.getId()));
    }

    @Test
    void givenNewTeacherWithSubjects_whenCreate_thenNewRowsInTableTeachersSubjectsCreated() {
	Teacher teacher = getTeacher();
	List<Subject> expected = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build()));
	teacher.setSubjects(expected);

	teacherDao.create(teacher);

	List<Subject> actual = hibernateTemplate.get(Teacher.class, teacher.getId()).getSubjects();
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher expected = getTeacher();
	expected.setFirstName("new name");
	expected.setBirthDate(LocalDate.of(1984, 01, 01));

	teacherDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Teacher.class, expected.getId()));
    }

    @Test
    void givenNewSubjectsOfTeacher_whenUpdate_thenTeachersSubjectUpdated() {
	Teacher teacher = getTeacher();
	List<Subject> expected = new ArrayList<>(Arrays.asList(Subject.builder().id(4).name("subject 4").build(),
		Subject.builder().id(3).name("subject 3").build()));
	teacher.setSubjects(expected);

	teacherDao.update(teacher);

	assertEquals(expected, hibernateTemplate.get(Teacher.class, teacher.getId()).getSubjects());
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Teacher expected = getTeacher();

	Teacher actual = teacherDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenGetAll_thenFound() {
	List<Teacher> expected = new ArrayList<>();
	expected.add(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build());
	expected.add(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
		.birthDate(LocalDate.of(2002, 02, 02)).address("address 2").phone("0670000002").email("email 2")
		.degree("0").gender(Gender.FEMALE).build());

	List<Teacher> actual = teacherDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Teacher teacher = getTeacher();

	teacherDao.delete(teacher);

	assertEquals(null, hibernateTemplate.get(Teacher.class, teacher.getId()));
    }

    @Test
    void givenSubject_whenGetBySubject_thenGot() {
	List<Teacher> expected = new ArrayList<>();
	expected.add(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build());
	expected.add(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
		.birthDate(LocalDate.of(2002, 02, 02)).address("address 2").phone("0670000002").email("email 2")
		.degree("0").gender(Gender.FEMALE).build());
	Subject subject = Subject.builder().id(1).name("subject 1").build();

	List<Teacher> actual = teacherDao.getBySubject(subject);

	assertEquals(expected, actual);
    }

    private Teacher getTeacher() {
	return Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
    }

}
