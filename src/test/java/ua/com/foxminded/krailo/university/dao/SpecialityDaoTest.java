package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Speciality;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class SpecialityDaoTest {

    @Autowired
    private SpecialityDao specialityDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewSpeciality_whenCreate_thenCreated() {
	Speciality speciality = Speciality.builder().name("new name").
		faculty(Faculty.builder().id(1).name("new").build()).
		build();

	specialityDao.create(speciality);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "specialities");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfFaculty_whenUpdate_tnenUpdated() {
	Speciality speciality = Speciality.builder().id(1).name("new name").
		faculty(Faculty.builder().id(1).name("new").build()).
		build();

	specialityDao.update(speciality);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities",
		"name = 'new name' AND faculty_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities", "id =1");

	int actual = specialityDao.findById(1).get().getId();

	assertEquals(expected, actual);
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
    void givenSpecialityNameAndFaculyId_whenFindByNamrAndFacultyId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "specialities", "name = 'speciality 1' and faculty_id  = 1");

	int actual = specialityDao.findByNameAndFacultyId("speciality 1", 1).get().getId();

	assertEquals(expected, actual);
    }

}
