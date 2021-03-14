package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
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
	Audience audience = Audience.builder().number("3")
		.building(Building.builder().id(2).name("name").address("address").build()).capacity(120)
		.description("description3").build();

	audienceDao.create(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "id =" + audience.getId());
	assertEquals(1, actual);
    }
 
    @Test
    void givenAudienceWithExistingNumberAndBuildingId_whenCreate_thenDaoConstraintViolationExceptionThrown() {
	Audience audience = Audience.builder().number("1")
		.building(Building.builder().id(2).name("name").address("address").build()).capacity(120)
		.description("description3").build();
	
	String actual = assertThrows(DaoConstraintViolationException.class, () -> audienceDao.create(audience))
		.getMessage();
	
	String expected = "Audiences not created, audience=0-1-2-name-address-120-description3";
	assertEquals(actual, expected);
    }

    @Test
    void givenBuildingId_whenFindByBuildingId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "building_id = 1");

	int actual = audienceDao.findByBuildingId(1).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenAudience_whenFindByNumber_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "Audiences", "number = 1");

	int actual = audienceDao.findByNumber("1").size();

	assertEquals(expected, actual);
    }

    @Test
    void givenAudiences_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");

	int actual = audienceDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {

	Audience actual = audienceDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenNotExistingId_whenFindById_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.findById(10);

	assertEquals(Optional.empty(), actual);
    }

    @Test 
    void givenNewFieldsOfAudience_whenUpdate_thenUpdated() {
	Audience audience = Audience.builder().id(1).number("new")
		.building(Building.builder().id(1).name("new name").address("new address").build()).capacity(1)
		.description("new").build();

	audienceDao.update(audience);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "audiences",
		"number = 'new' AND building_id = 1 AND capacity = 1 AND description = 'new'");
	assertEquals(1, actual);
    }
    
    @Test
    void givenAudienceWithExistingNumberAndBuildingId_whenUpdate_thenDaoConstraintViolationExceptionThrown() {
	Audience audience = Audience.builder().id(1).number("2")
		.building(Building.builder().id(2).name("name").address("address").build()).capacity(120)
		.description("description3").build();
	
	String actual = assertThrows(DaoConstraintViolationException.class, () -> audienceDao.update(audience))
		.getMessage();
	
	String expected = "Audience not updated audience1-2-2-name-address-120-description3";
	assertEquals(actual, expected);
    }

    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {

	audienceDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "Audiences");
	assertEquals(2, actual);
    }

    @Test
    void givenAudience_whenFindByNumberAndBuildingId_thenFound() {

	Audience actual = audienceDao.findByNumberAndBuildingId("1", 1).get();

	assertEquals(1, actual.getId());
	assertEquals(1, actual.getBuilding().getId());
    }

    @Test
    void givenNotExistingAudienceNumber_whenFindByNumberAndBuildingId_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.findByNumberAndBuildingId("10", 1);

	assertEquals(Optional.empty(), actual);
    }

    @Test
    void givenNotExistingBuildingId_whenFindByNumberAndBuildingId_thenEmptyOptional() {

	Optional<Audience> actual = audienceDao.findByNumberAndBuildingId("1", 10);

	assertEquals(Optional.empty(), actual);
    }

}
