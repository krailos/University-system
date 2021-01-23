package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Department;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewDepartment_whenCreate_thenCreated() {
	Department department = new Department("new");
	departmentDao.create(department);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "departments");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfDepartment_whenUpdate_tnenUpdated() {
	Department department = new Department(1, "new");
	departmentDao.update(department);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "departments", "name = 'new'");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "departments", "id =1");
	int actual = departmentDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenDepartments_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "departments");
	int actual = departmentDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	departmentDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "departments");
	assertEquals(expected, actual);
    }

}
