package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;
import ua.com.foxminded.krailo.university.testConfig.ConfigTest;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewGroup_whenCreate_thenCreated() {
	Group group = new Group("new", new Year(1, "new", null));
	groupDao.create(group);
	int expected = 3;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfGroup_whenUpdate_tnenUpdated() {
	Group group = new Group(1, "new", new Year(1, "new", null));
	groupDao.update(group);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "name = 'new' AND id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "groups", "id = 1");
	int actual = groupDao.findById(1).getId();
	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	int actual = groupDao.findAll().size();
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {
	groupDao.deleteById(1);
	int expected = 1;
	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups");
	assertEquals(expected, actual);
    }

}
