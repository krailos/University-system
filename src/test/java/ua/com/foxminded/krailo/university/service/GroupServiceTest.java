package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
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
	Group group = Group.builder().id(1).name("name1").year(Year.builder().id(1).build()).build();
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
	Group group = Group.builder().id(1).name("name1").year(Year.builder().id(1).build()).build();
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
	return Group.builder().id(1).name("name").year(Year.builder().id(1).build()).build();
    }

    private List<Group> createGroups() {
	return new ArrayList<Group>(
		Arrays.asList(Group.builder().id(1).name("name1").year(Year.builder().id(1).build()).build(),
			Group.builder().id(2).name("name2").year(Year.builder().id(1).build()).build()));
    }

}
