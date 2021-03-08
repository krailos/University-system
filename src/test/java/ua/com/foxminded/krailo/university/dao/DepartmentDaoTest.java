package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Department;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewDepartment_whenCreate_thenCreated() {
	Department department = Department.builder().
		name("new department").build();

	departmentDao.create(department);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "departments");
	assertEquals(3, actual);
    }

    @Test
    void givenNewFieldsOfDepartment_whenUpdate_tnenUpdated() {
	Department department = new Department.DepartmentBuilder().
		id(1).
		name("new department").build();

	departmentDao.update(department);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "departments", "name = 'new department'");
	assertEquals(1, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "departments", "id =1");

	int actual = departmentDao.findById(1).get().getId();

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

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "departments");
	assertEquals(1, actual);
    }

}
