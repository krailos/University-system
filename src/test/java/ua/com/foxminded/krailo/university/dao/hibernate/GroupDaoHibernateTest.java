package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.GroupDaoInt;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class GroupDaoHibernateTest {

    @Autowired
    private GroupDaoInt groupDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewGroup_whenCreate_thenCreated() {
	Group group = getGroup();
	group.setId(0);

	groupDao.create(group);

	assertEquals(group, hibernateTemplate.get(Group.class, 3));
    }

    @Test
    void givenNewGroupName_whenUpdate_tnenUpdated() {
	Group group = getGroup();
	group.setName("new name");

	groupDao.update(group);

	assertEquals(group.getName(), hibernateTemplate.get(Group.class, 1).getName());
    }

    @Test
    void givenId_whenGetById_thenGot() {

	Group actual = getGroup();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenGroups_whenGetAll_thenGot() {

	int actual = groupDao.getAll().size();

	assertEquals(2, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Group group = getGroup();

	groupDao.delete(group);

	assertEquals(null, hibernateTemplate.get(Group.class, 1));
    }

    @Test
    void givenLessonId_whenGetByYear_thenGot() {
	Group group = getGroup();

	List<Group> groups = groupDao.getByYear(group.getYear());

	assertEquals(2, groups.size());
    }

    @Test
    void givenGroupNameAndYear_whenGetByNameAndYear_thenGot() {
	String groupName = "group 1";
	Year year = Year.builder().id(1).name("year 1").build();

	Group actual = groupDao.getByNameAndYear(groupName, year).get();

	assertEquals(groupName, actual.getName());
	assertEquals(year.getId(), actual.getYear().getId());
    }

    @Test
    void givnGroups_whengetByPage_thenGot() {
	int pageNumber = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	List<Group> actual = groupDao.getByPage(pageable);

	assertEquals(2, actual.size());
    }

    @Test
    void givenGroups_whenCount_thenCounted() {

	int actual = groupDao.count();

	assertEquals(2, actual);
    }

    private Group getGroup() {
	return Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build();
    }

}
