package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class BuildingDaoTest {
    
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Test
    void givenNewBuilding_whenCreate_thenCreated() {
	Building building = new Building("Building 3", "Address 3");
	buildingDao.create(building);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "buildings");
	assertEquals(expected, actual);
    }
    
    @Test
    void givenNewFieldsOfBuilding_whenUpdate_thenUpdated() {
	Building building = new Building(1, "new", "new");
	buildingDao.update(building);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "buildings", " name = 'new' and address = 'new'");
	assertEquals(expected, actual);
    }
    
    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "buildings", "id = 1");
	int actual = buildingDao.findById(1).getId();
	assertEquals(expected, actual);
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
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "buildings");
	assertEquals(expected, actual);
    }


}
