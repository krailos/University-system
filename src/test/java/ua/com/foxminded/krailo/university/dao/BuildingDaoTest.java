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
    void test() {
	Building building = new Building("Building 3", "Address 3");
	buildingDao.create(building);
    }
    
    @Test
    void givenNewFieldsOfBuilding_whenUpdate_thenUpdated() {
	Building building = new Building(1, "new", "new");
	buildingDao.update(building);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "buildings", " name = 'new' and address = 'new'");
	assertEquals(expected, actual);
    }

}
