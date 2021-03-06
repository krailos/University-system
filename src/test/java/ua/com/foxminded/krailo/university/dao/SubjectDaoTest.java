package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@DataJpaTest
@ContextConfiguration(classes = ConfigTest.class)
class SubjectDaoTest {

    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenNewSubject_whenCreate_thenCreated() {
	Subject expected = getSubject();
	expected.setId(0);

	subjectDao.save(expected);

	assertEquals(expected, entityManager.find(Subject.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfSubject_whenUpdate_tnenUpdated() {
	Subject expected = getSubject();
	expected.setName("new name");

	subjectDao.save(expected);

	assertEquals(expected, entityManager.find(Subject.class, expected.getId()));
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Subject expected = getSubject();

	Subject actual = subjectDao.findById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenSubjects_whenGetAll_thenGot() {
	List<Subject> expected = getSubjects();

	List<Subject> actual = subjectDao.findAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Subject subject = getSubject();

	subjectDao.delete(subject);

	assertNull(entityManager.find(Subject.class, subject.getId()));
    }

    @Test
    void givenTeacher_whenGetSubjectsByTeacher_thenGot() {
	Teacher teacher = Teacher.builder().id(1).build();
	List<Subject> expected = new ArrayList<>();
	expected.add(Subject.builder().id(1).name("subject 1").build());
	expected.add(Subject.builder().id(2).name("subject 2").build());

	List<Subject> actual = subjectDao.findByTeachers(teacher);

	assertEquals(expected, actual);
    }

    @Test
    void givenYearId_whenGetSubjectsByYear_thenGot() {
	Year year = Year.builder().id(1).name("year first").build();
	List<Subject> expected = new ArrayList<>();
	expected.add(Subject.builder().id(1).name("subject 1").build());
	expected.add(Subject.builder().id(2).name("subject 2").build());

	List<Subject> actual = subjectDao.findByYears(year);

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
