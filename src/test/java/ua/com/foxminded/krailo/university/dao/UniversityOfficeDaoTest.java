package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class UniversityOfficeDaoTest {

    @Autowired
    private UniversityOfficeDao universityOfficeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenId_whenFindById_thenFound() {
	
	UniversityOffice actual = universityOfficeDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenUniversityOffices_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");

	int actual = universityOfficeDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	universityOfficeDao.deleteById(2);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");
	assertEquals(1, actual);
    }

    @Test
    void givenNewFieldsOfUniversity_office_whenUpdate_thenUpdated() {
	UniversityOffice universityOffice = UniversityOffice.builder().
		id(1).name("new name").address("new address").build();

	universityOfficeDao.update(universityOffice);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "university_office",
		"name = 'new name' AND address = 'new address'");
	assertEquals(1, actual);
    }

    @Test
    void givenNewUniversity_office_whenCreate_thenCreated() {
	UniversityOffice universityOffice = UniversityOffice.builder().
		name("new name").address("new address").build();

	universityOfficeDao.create(universityOffice);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "university_office ");
	assertEquals(3, actual);
    }
}
