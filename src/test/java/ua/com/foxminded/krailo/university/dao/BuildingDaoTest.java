package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.config.WebConfig;
import ua.com.foxminded.krailo.university.model.Building;

@SpringJUnitWebConfig(classes = { WebConfig.class, ConfigTest.class })
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class BuildingDaoTest {

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test 
    void givenNewBuilding_whenCreate_thenCreated() {
	Building building = Building.builder().name("Building 3").address("Address 3").build();

	buildingDao.create(building);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "buildings");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfBuilding_whenUpdate_thenUpdated() {
	Building building = new Building.BuildingBuilder().id(1).name("new name").address("new address").build();

	buildingDao.update(building);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "buildings",
		" name = 'new name' and address = 'new address'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	
	Building actual = buildingDao.findById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenBuildings_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "buildings");

	int actual = buildingDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() throws Exception {

	buildingDao.deleteById(2);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "buildings");
	assertEquals(1, actual);
    }

}
