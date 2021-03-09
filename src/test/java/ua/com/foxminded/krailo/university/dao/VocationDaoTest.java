package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class VocationDaoTest {

    @Autowired
    private VocationDao vocationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewVocation_whenCreate_thenCreated() {
	Vocation vocation = Vocation.builder().kind(VocationKind.GENERAL).
		applyingDate(LocalDate.of(2000, 01, 01)).
		startDate(LocalDate.of(2000, 02, 02)).
		endDate(LocalDate.of(2000, 03, 03)).
		teacher(Teacher.builder().id(1).build()).
		build();
	
	vocationDao.create(vocation);
	
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vocations");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfVocation_whenUpdate_thenUpdated() {
	Vocation vocation = Vocation.builder().id(1).
		kind(VocationKind.GENERAL).
		applyingDate(LocalDate.of(2021, 10, 01)).
		startDate(LocalDate.of(2021, 10, 02)).
		endDate(LocalDate.of(2021, 10, 10)).
		teacher(Teacher.builder().id(1).build()).
		build();
	
	vocationDao.update(vocation);
	
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations",
		"kind = 'GENERAL' AND start_date = '2021-10-02' AND end_date = '2021-10-10'AND teacher_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations", "id = 1");
	
	int actual = vocationDao.findById(1).get().getId();
	
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
    
    @Test
    void givenId_whenFindByTeacherIdAndDate_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "vocations", "id = 1 AND '2021-02-07' BETWEEN start_date AND end_date");
	
	int actual = vocationDao.findByTeacherIdAndDate(1, LocalDate.of(2021, 02, 07)).get().getId();
	
	assertEquals(expected, actual);
    }
    
    @Test
    void givenVocation_whenFindByTeacherIdAndYear_thenFound() {
	Vocation vocation = Vocation.builder().id(1).
		kind(VocationKind.GENERAL).
		applyingDate(LocalDate.of(2000, 01, 01)).
		startDate(LocalDate.of(2021, 02, 02)).
		endDate(LocalDate.of(2021, 03, 03)).
		teacher(Teacher.builder().id(1).build()).
		build();
	
	int actual = vocationDao.findByTeacherIdAndYear(vocation.getTeacher().getId(), Year.from(vocation.getStart())).size();
	
	assertEquals(1, actual);
    }

}
