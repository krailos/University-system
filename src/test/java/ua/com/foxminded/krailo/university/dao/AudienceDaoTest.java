package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class AudienceDaoTest {

    @Autowired
    private AudienceDao audienceDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewAudience_whenCreate_thenCreated() throws Exception {
	Audience audience = new Audience.AudienceBuilder().withNumber("3")
		.withBuilding(new Building.BuildingBuilder().withId(2).withName("name").withAddress("address").built())
		.withCapacity(120).withDescription("description3").built();

	audienceDao.create(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "id =" + audience.getId());
	assertEquals(1, actual);
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
	Audience audience = new Audience.AudienceBuilder().withId(1).withNumber("new").withBuilding(
		new Building.BuildingBuilder().withId(1).withName("new name").withAddress("new address").built())
		.withCapacity(1).withDescription("new").built();

	audienceDao.update(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "audiences",
		"number = 'new' AND building_id = 1 AND capacity = 1 AND description = 'new'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {

	audienceDao.deleteById(3);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");
	assertEquals(2, actual);
    }

}
