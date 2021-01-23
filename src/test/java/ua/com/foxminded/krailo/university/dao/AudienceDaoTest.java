package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = new Audience("3", new Building(2, "name", "address"), 120, "description3");
	audienceDao.create(audience);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "id =" + audience.getId());
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildingId_whenFindByBuildingId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "building_id = 1");
	int actual = audienceDao.findByBuildingId(1).size();
	assertEquals(expected, actual);
    }

    @Test
    void givenBuldings_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");
	int actual = audienceDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "id = 1");
	int actual = audienceDao.findById(1).getId();
	assertEquals(expected, actual);
    }
    
    @Test
    void givenNewFieldsOfAudience_whenUpdate_thenUpdated() {
	Audience audience = new Audience(1, "new", new Building(1, "", ""), 1, "new");
	audienceDao.update(audience);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "audiences",
		"number = 'new' AND building_id = 1 AND capacity = 1 AND description = 'new'");
	assertEquals(expected, actual);
    }
	
    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {
	audienceDao.deleteById(3);
	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");
	assertEquals(expected, actual);
    }

}
