package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HibernateGroupDaoTest {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewGroup_whenCreate_thenCreated() {
	Group group = getGroup();
	group.setId(0);

	groupDao.create(group);

	assertEquals(group, hibernateTemplate.get(Group.class, group.getId()));
    }

    @Test
    void givenNewGroupName_whenUpdate_tnenUpdated() {
	Group expected = getGroup();
	expected.setName("new name");

	groupDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Group.class, expected.getId()));
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Group expected = getGroup();

	Group actual = getGroup();

	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenGetAll_thenGot() {
	List<Group> expected = getGroups();

	List<Group> actual = groupDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Group group = getGroup();

	groupDao.delete(group);

	assertNull(hibernateTemplate.get(Group.class, group.getId()));
    }

    @Test
    void givenLessonId_whenGetByYear_thenGot() {
	Year year = Year.builder().id(1).name("first").build();
	List<Group> expected = getGroups();

	List<Group> actual = groupDao.getByYear(year);

	assertEquals(expected, actual);
    }

    @Test
    void givenGroupNameAndYear_whenGetByNameAndYear_thenGot() {
	Group expected = getGroup();
	String groupName = "group 1";
	Year year = Year.builder().id(1).name("year 1").build();

	Group actual = groupDao.getByNameAndYear(groupName, year).get();

	assertEquals(expected, actual);
    }

    @Test
    void givnGroups_whengetByPage_thenGot() {
	int pageNumber = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	Page<Group> expected = new PageImpl<>(getGroups(), pageable, 2);

	Page<Group> actual = groupDao.getAll(pageable);

	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenCount_thenCounted() {

	int actual = groupDao.count();

	assertEquals(hibernateTemplate.execute( session -> session.createQuery("from Group").list().size()), actual);
    }

    private Group getGroup() {
	return Group.builder().id(1).name("group 1").build();
    }

    private List<Group> getGroups() {
	List<Group> groups = new ArrayList<>();
	groups.add(Group.builder().id(1).name("group 1").build());
	groups.add(Group.builder().id(2).name("group 2").build());
	return groups;
    }

}
