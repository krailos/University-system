package ua.com.foxminded.krailo.university.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.testConfig.TestConfig;

@SpringJUnitConfig(TestConfig.class)
class BuildingDaoTest extends DataSourceBasedDBTestCase {

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private DataSource dataSource;
    private IDataSet iDataSet;

    @Override
    protected IDataSet getDataSet() throws Exception {
	iDataSet = new FlatXmlDataSetBuilder()
		.build(getClass().getClassLoader().getResourceAsStream("xmlTestData.xml"));
	return iDataSet;
    }

    @Override
    protected DataSource getDataSource() {
	return dataSource;

    }

    @BeforeEach
    public void init() throws SQLException {
	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	populator.addScript(new ClassPathResource("schemaTest.sql"));
	populator.addScript(new ClassPathResource("dataTest.sql"));
	DatabasePopulatorUtils.execute(populator, dataSource);
    }

    @Test
    void givenNewBulding_whenAdd_thanAdded() throws Exception {
	Building building = new Building("Building 3", "Address 3");
	buildingDao.addBuilding(building);
	ITable expected = getDataSet().getTable("BUILDINGS");
	ITable actual = getConnection().createDataSet().getTable("BUILDINGS");
	Assertion.assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFind_thanFinded() {
	Building building = new Building(1, "Building 1", "Address 1");
	Building expected = building;
	Building actual = buildingDao.findById(1);
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildingsTable_whenFindAll_thanFinded() {
	List<Building> buildings = Arrays.asList(new Building(1, "Building 1", "Address 1"),
		new Building(2, "Building 2", "Address 2"));
	List<Building> expected = buildings;
	List<Building> actual = buildingDao.findAll();
	assertEquals(expected, actual);
    }

}
