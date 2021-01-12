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

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.testConfig.TestConfig;

@SpringJUnitConfig(TestConfig.class) 
class AudienceDaoTest extends DataSourceBasedDBTestCase {

    @Autowired
    private AudienceDao audienceDao;
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
    void givenNewAudience_whenAdd_thanAdded() throws Exception {
	Audience audience = new Audience(4, "3", new Building(2, "name", "address"), 120, "description3");
	audienceDao.addAudience(audience);
	ITable expected = getDataSet().getTable("AUDIENCES_ADD");
	ITable actual = getConnection().createDataSet().getTable("AUDIENCES");
	Assertion.assertEquals(expected, actual);
    }

    @Test
    void givenBuildingId_whenFind_thanFinded() {
	Building building = new Building(1, "Building 1", "Address 1");
	List<Audience> audiences = Arrays.asList(new Audience(1, "1", building, 300, "description1"));
	List<Audience> expected = audiences;
	List<Audience> actual = audienceDao.findAudiencesByBuildingId(1);
	assertEquals(expected, actual);
    }

    @Test
    void givenBuldings_whenFindAll_thanFinded() {
	List<Audience> audiences = Arrays.asList(new Audience(1, "1", null, 300, "description1"),
		new Audience(2, "1", null, 120, "description1"), new Audience(3, "2", null, 120, "description2"));
	List<Audience> expected = audiences;
	List<Audience> actual = audienceDao.findAll();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFind_thanFinded () {
	Building building = new Building(1, "Building 1", "Address 1");
	Audience expected = new Audience(1, "1", building, 300, "description1");
	Audience actual = audienceDao.findById(1);
	assertEquals(expected, actual);
    }
    
    @Test
    void givenId_whenDelete_thanDeleted () throws Exception {
	audienceDao.deleteById(3);
	ITable expected = getDataSet().getTable("AUDIENCES_DELETE");
	ITable actual = getConnection().createDataSet().getTable("AUDIENCES");
	Assertion.assertEquals(expected, actual);
    }
    
}
