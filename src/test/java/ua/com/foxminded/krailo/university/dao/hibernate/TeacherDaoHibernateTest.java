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
import ua.com.foxminded.krailo.university.dao.interf.TeacherDaoInt;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class TeacherDaoHibernateTest {

    @Autowired
    private TeacherDaoInt teacherDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewTeacher_whenCreate_thenCreated() {
	Teacher teacher = getTeacher();
	teacher.setId(0);

	teacherDao.create(teacher);

	assertEquals(teacher, hibernateTemplate.get(Teacher.class, 3));
    }

    @Test
    void givenNewTeacherWithSubjects_whenCreate_thenNewRowsInTableTeachersSubjectsCreated() {
	Teacher teacher = getTeacher();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build()));
	teacher.setSubjects(subjects);

	teacherDao.create(teacher);

	int expected = 2;
	int actual = hibernateTemplate.get(Teacher.class, 3).getSubjects().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfTeacher_whenUpdate_tnenUpdated() {
	Teacher teacher = getTeacher();
	teacher.setFirstName("new name");
	teacher.setBirthDate(LocalDate.of(1984, 01, 01));

	teacherDao.update(teacher);

	assertEquals(teacher.getFirstName(), hibernateTemplate.get(Teacher.class, 1).getFirstName());
	assertEquals(teacher.getBirthDate(), hibernateTemplate.get(Teacher.class, 1).getBirthDate());
    }

    @Test
    void givenNewSubjectsOfTeacher_whenUpdate_thenUpdated() {
	Teacher teacher = getTeacher();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("subject1").build(),
		Subject.builder().id(2).name("subject2").build()));
	teacher.setSubjects(subjects);

	teacherDao.update(teacher);

	assertEquals(teacher.getSubjects().size(), hibernateTemplate.get(Teacher.class, 1).getSubjects().size());
    }

    @Test
    void givenId_whenGetById_thenGot() {

	Teacher actual = teacherDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenTeachers_whenGetAll_thenFound() {

	int actual = teacherDao.getAll().size();

	assertEquals(2, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Teacher teacher = getTeacher();

	teacherDao.delete(teacher);

	assertEquals(null, hibernateTemplate.get(Teacher.class, 1));
    }

    @Test
    void givenSubject_whenGetBySubject_thenGot() {
	Subject subject = Subject.builder().id(1).name("subject 1").build();

	List<Teacher> actual = teacherDao.getBySubject(subject);

	assertEquals(2, actual.size());
    }

    private Teacher getTeacher() {
	return Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
    }

}
