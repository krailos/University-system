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
	Subject subject = new Subject("new");

	subjectDao.create(subject);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
	assertEquals(5, actual);
    }

    @Test
    void givenNewFieldsOfSubject_whenUpdate_tnenUpdated() {
	Subject department = new Subject(1, "new");

	subjectDao.update(department);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "name = 'new'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "id =1");

	int actual = subjectDao.findById(1).getId();

	assertEquals(expected, actual);
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
	Teacher teacher = new Teacher(1);
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "id = 1 OR id = 2" );

	int actual = subjectDao.findByTeacherId(teacher.getId()).size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenYearId_whenFindSubjectsByYearId_thenFound() {
	Year year = new Year(1, "", null);
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "subjects", "id = 1 OR id = 2" );

	int actual = subjectDao.findByYearId(year.getId()).size();

	assertEquals(expected, actual);
    }

}
