package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class SubjectDaoTest {

    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewSubject_whenCreate_thenCreated() {
	Subject subject = Subject.builder().name("new subject").build();

	subjectDao.create(subject);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
	assertEquals(5, actual);
    }

    @Test
    void givenNewFieldsOfSubject_whenUpdate_tnenUpdated() {
	Subject subject = Subject.builder().id(1).name("new subject").build();


	subjectDao.update(subject);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "name = 'new subject'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Subject actual = subjectDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenSubjects_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");

	int actual = subjectDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	subjectDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
	assertEquals(3, actual);
    }
    
    @Test
    void givenTeacherId_whenFindSubjectsByTeacherId_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "id = 1 OR id = 2" );

	int actual = subjectDao.findByTeacherId(teacher.getId()).size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenYearId_whenFindSubjectsByYearId_thenFound() {
	Year year = Year.builder().id(1).name("new year").build();
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "id = 1 OR id = 2" );

	int actual = subjectDao.findByYearId(year.getId()).size();

	assertEquals(expected, actual);
    }

}
