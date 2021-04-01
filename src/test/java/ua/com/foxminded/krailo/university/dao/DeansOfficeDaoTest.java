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
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class DeansOfficeDaoTest {

    @Autowired
    private DeansOfficeDao deansOfficeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewDeansOffice_whenCreate_thenCreated() {
	DeansOffice deansOffice = DeansOffice.builder().name("new name")
		.universityOffice(UniversityOffice.builder().id(1).name("new name")
			.address("new address").build())
		.build();

	deansOfficeDao.create(deansOffice);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfDeansOffice_whenUpdate_tnenUpdated() {
	DeansOffice deansOffice =  DeansOffice.builder().id(1).name("new name")
		.universityOffice(UniversityOffice.builder().id(1).name("new name")
			.address("new address").build())
		.build();

	deansOfficeDao.update(deansOffice);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "deans_office",
		"name = 'new name' AND university_office_id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	DeansOffice actual = deansOfficeDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenDeansOffices_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");

	int actual = deansOfficeDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	deansOfficeDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "deans_office");
	assertEquals(1, actual);
    }

}
