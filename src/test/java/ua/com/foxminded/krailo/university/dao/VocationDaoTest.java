package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class VocationDaoTest {

    @Autowired
    private VocationDao vocationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewVocation_whenCreate_thenCreated() {
	Vocation vocation = new Vocation.VocationBuilder().withKind("kind").
		withApplyingDate(LocalDate.of(2000, 01, 01)).
		withStartDate(LocalDate.of(2000, 02, 02)).
		withEndDate(LocalDate.of(2000, 03, 03)).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		built();
	
	vocationDao.create(vocation);
	
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfVocation_whenUpdate_thenUpdated() {
	Vocation vocation = new Vocation.VocationBuilder().withId(1).
		withKind("kind").
		withApplyingDate(LocalDate.of(2000, 01, 01)).
		withStartDate(LocalDate.of(2000, 02, 02)).
		withEndDate(LocalDate.of(2000, 03, 03)).
		withTeacher(new Teacher.TeacherBuilder().withId(1).built()).
		built();
	
	vocationDao.update(vocation);
	
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations",
		"kind = 'kind' AND applying_date = '2000-01-01' AND start_date = '2000-02-02' AND end_date = '2000-03-03'AND teacher_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations", "id = 1");
	
	int actual = vocationDao.findById(1).getId();
	
	assertEquals(expected, actual);
    }

    @Test
    void givenVocations_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	
	int actual = vocationDao.findAll().size();
	
	assertEquals(expected, actual);
    }
    
    @Test
    void givenTeacherId_whenFindByTeacherId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations", "teacher_id = 1");
	
	int actual = vocationDao.findByTeacherId(1).size();
	
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {
	
	vocationDao.deleteById(2);
	
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	assertEquals(1, actual);
    }

}
