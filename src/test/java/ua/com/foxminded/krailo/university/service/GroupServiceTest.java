package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class GroupServiceTest {

    @Mock
    private GroupDao groupDao;
    @Mock
    private StudentDao studentDao;
    @InjectMocks
    private GroupService groupService;

    @Test
    void givenGroup_whenCereate_thanCreated() {
	Group group = createGroup();
	doNothing().when(groupDao).create(group);

	groupService.create(group);

	verify(groupDao).create(group);
    }
    
    @Test
    void givenGroupWithExistingName_whenCereate_thanNotCreated() {
	Group group = new Group.GroupBuilder().withId(1).withName("name1").withYear(new Year.YearBuilder().withId(1).built())
		.built();
	List<Group> groups = createGroups();
	when(groupDao.findByYearId(1)).thenReturn(groups);

	groupService.create(group);

	verify(groupDao, never()).create(group);
    }

    @Test
    void givenGroup_whenUpdate_thanUpdeted() {
	Group group = createGroup();
	doNothing().when(groupDao).update(group);

	groupService.update(group);

	verify(groupDao).update(group);
    }
    
    @Test
    void givenGroupWithExistingName_whenUpdate_thanNotUpdated() {
	Group group = new Group.GroupBuilder().withId(1).withName("name1").withYear(new Year.YearBuilder().withId(1).built())
		.built();
	List<Group> groups = createGroups();
	when(groupDao.findByYearId(1)).thenReturn(groups);

	groupService.update(group);

	verify(groupDao, never()).update(group);
    }

    @Test
    void givenGroupId_whenGetById_thenGot() {
	Group group = createGroup();
	when(groupDao.findById(1)).thenReturn(group);
	Group expected = createGroup();

	Group actual = groupService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenGetAll_thenGot() {
	List<Group> groups = createGroups();
	when(groupDao.findAll()).thenReturn(groups);

	List<Group> actual = groupService.getAll();

	List<Group> expected = createGroups();
	assertEquals(expected, actual);
    }

    @Test
    void givenGroup_whenDelete_thenDeleted() {
	Group group = createGroup();
	doNothing().when(groupDao).deleteById(1);

	groupService.delete(group);

	verify(groupDao).deleteById(1);
    }

    private Group createGroup() {
	return new Group.GroupBuilder().withId(1).withName("name").withYear(new Year.YearBuilder().withId(1).built())
		.built();
    }

    private List<Group> createGroups() {
	return new ArrayList<Group>(Arrays.asList(
		new Group.GroupBuilder().withId(1).withName("name1").withYear(new Year.YearBuilder().withId(1).built())
			.built(),
		new Group.GroupBuilder().withId(2).withName("name2").withYear(new Year.YearBuilder().withId(1).built())
			.built()));
    }

}
