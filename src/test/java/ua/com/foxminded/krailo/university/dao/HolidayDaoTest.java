package ua.com.foxminded.krailo.university.dao;

import java.sql.SQLException;
import java.time.LocalDate;
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

import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.testConfig.TestConfig;

@SpringJUnitConfig(TestConfig.class)
class HolidayDaoTest extends DataSourceBasedDBTestCase {

    @Autowired
    private HolidayDao holidayDao;
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
    void givenHolidays_whenFindAll_thanFinded() throws Exception {
	List<Holiday> expected = Arrays.asList(new Holiday(1, "Holiday 1", LocalDate.of(2021, 01, 01)),
		new Holiday(2, "Holiday 2", LocalDate.of(2021, 01, 07)));
	List<Holiday> actual = holidayDao.findAll();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thanFinded() throws Exception {
	Holiday expected = new Holiday(1, "Holiday 1", LocalDate.of(2021, 01, 01));
	Holiday actual = holidayDao.findById(1);
	assertEquals(expected, actual);
    }

    @Test
    void givenNewHoliday_whenAdd_thanAdded() throws Exception {
	Holiday holiday = new Holiday(3, "Holiday 3", LocalDate.of(2021, 01, 19));
	holidayDao.addHoliday(holiday);
	ITable expected = getDataSet().getTable("HOLIDAYS_ADD");
	ITable actual = getConnection().createDataSet().getTable("holidays");
	Assertion.assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thanDeleted() throws Exception {
	holidayDao.deleteById(3);
	ITable expected = getDataSet().getTable("HOLIDAYS_DELETE");
	ITable actual = getConnection().createDataSet().getTable("holidays");
	Assertion.assertEquals(expected, actual);
    }
    
    @Test
    void givenNameAndId_whenUpdeteName_thanUpdated() throws Exception {
	holidayDao.updateNameById("Holiday update", 1);;
	ITable expected = getDataSet().getTable("HOLIDAYS_UPDATE_NAME");
	ITable actual = getConnection().createDataSet().getTable("holidays");
	Assertion.assertEquals(expected, actual);
    }
    
    @Test
    void givendATEAndId_whenUpdetedATE_thanUpdated() throws Exception {
	holidayDao.updateDateById(LocalDate.of(2021, 01,  02), 1);;
	ITable expected = getDataSet().getTable("HOLIDAYS_UPDATE_DATE");
	ITable actual = getConnection().createDataSet().getTable("holidays");
	Assertion.assertEquals(expected, actual);
    }

}
