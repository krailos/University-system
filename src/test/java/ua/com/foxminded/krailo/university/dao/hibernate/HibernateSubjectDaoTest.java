package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(SpringExtension.class)
@Transactional
@Import(ConfigTest.class)
@WebAppConfiguration
class HibernateSubjectDaoTest {

    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewSubject_whenCreate_thenCreated() {
	Subject expected = getSubject();
	expected.setId(0);

	subjectDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Subject.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfSubject_whenUpdate_tnenUpdated() {
	Subject expected = getSubject();
	expected.setName("new name");

	subjectDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Subject.class, expected.getId()));
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Subject expected = getSubject();

	Subject actual = subjectDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenSubjects_whenGetAll_thenGot() {
	List<Subject> expected = getSubjects();

	List<Subject> actual = subjectDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Subject subject = getSubject();

	subjectDao.delete(subject);

	assertNull(hibernateTemplate.get(Subject.class, subject.getId()));
    }

    @Test
    void givenTeacher_whenGetSubjectsByTeacher_thenGot() {
	Teacher teacher = Teacher.builder().id(1).build();
	List<Subject> expected = new ArrayList<>();
	expected.add(Subject.builder().id(1).name("subject 1").build());
	expected.add(Subject.builder().id(2).name("subject 2").build());

	List<Subject> actual = subjectDao.getByTeacher(teacher);

	assertEquals(expected, actual);
    }

    @Test
    void givenYearId_whenGetSubjectsByYear_thenGot() {
	Year year = Year.builder().id(1).name("year first").build();
	List<Subject> expected = new ArrayList<>();
	expected.add(Subject.builder().id(1).name("subject 1").build());
	expected.add(Subject.builder().id(2).name("subject 2").build());

	List<Subject> actual = subjectDao.getByYear(year);

	assertEquals(expected, actual);
    }

    private Subject getSubject() {
	return Subject.builder().id(1).name("subject 1").build();
    }

    private List<Subject> getSubjects() {
	List<Subject> subjects = new ArrayList<>();
	subjects.add(Subject.builder().id(1).name("subject 1").build());
	subjects.add(Subject.builder().id(2).name("subject 2").build());
	subjects.add(Subject.builder().id(3).name("subject 3").build());
	subjects.add(Subject.builder().id(4).name("subject 4").build());
	return subjects;
    }

}
