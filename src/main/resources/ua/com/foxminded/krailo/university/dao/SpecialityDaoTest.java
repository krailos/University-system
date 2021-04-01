package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Specialty;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class SpecialityDaoTest {

    @Autowired
    private SpecialityDao specialityDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewSpeciality_whenCreate_thenCreated() {
	Specialty speciality = Specialty.builder().name("new name").
		faculty(Faculty.builder().id(1).name("new").build()).
		build();

	specialityDao.create(speciality);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfFaculty_whenUpdate_tnenUpdated() {
	Specialty speciality = Specialty.builder().id(1).name("new name").
		faculty(Faculty.builder().id(1).name("new").build()).
		build();

	specialityDao.update(speciality);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities",
		"name = 'new name' AND faculty_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	
	Specialty actual = specialityDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenSpecialities_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");

	int actual = specialityDao.findAll().size();

	assertEquals(expected, actual);
    }
    
    @Test
    void givenFaculyId_whenFindByFacultyId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities", "faculty_id  = 1");

	int actual = specialityDao.findByFacultyId(1).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	specialityDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	assertEquals(1, actual);
    }
    
    @Test
    void givenSpecialityNameAndFaculyId_whenFindByNameAndFacultyId_thenFound() {
	
	Specialty actual = specialityDao.findByNameAndFacultyId("speciality 1", 1).get();

	assertEquals("speciality 1", actual.getName());
	assertEquals(1, actual.getFaculty().getId());
    }

}
