package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.SubjectDaoInt;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class SubjectDaoHibernateTest {

    @Autowired
    private SubjectDaoInt subjectDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewSubject_whenCreate_thenCreated() {
	Subject subject = getSubject();
	subject.setId(0);

	subjectDao.create(subject);

	assertEquals(subject, hibernateTemplate.get(Subject.class, 5));
    }

    @Test
    void givenNewFieldsOfSubject_whenUpdate_tnenUpdated() {
	Subject subject = getSubject();
	subject.setName("new name");

	subjectDao.update(subject);

	assertEquals(subject.getName(), hibernateTemplate.get(Subject.class, 1).getName());
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Subject subject = getSubject();

	Subject actual = subjectDao.getById(1).get();

	assertEquals(subject.getId(), actual.getId());
    }

    @Test
    void givenSubjects_whenGetAll_thenGot() {

	int actual = subjectDao.getAll().size();

	assertEquals(4, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Subject subject = getSubject();

	subjectDao.delete(subject);

	assertEquals(null, hibernateTemplate.get(Subject.class, 1));
    }

    @Test
    void givenTeacher_whenGetSubjectsByTeacher_thenGot() {
	Teacher teacher = Teacher.builder().id(1).build();

	int actual = subjectDao.getByTeacher(teacher).size();

	assertEquals(2, actual);
    }

    @Test
    void givenYearId_whenGetSubjectsByYear_thenGot() {
	Year year = Year.builder().id(1).name("new year").build();

	int actual = subjectDao.getByYear(year).size();

	assertEquals(2, actual);
    }

    private Subject getSubject() {
	return Subject.builder().id(1).name("subject 1").build();
    }

}
