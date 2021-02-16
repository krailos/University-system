package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import ua.com.foxminded.krailo.university.model.Student;
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
	Group group = new Group("name", new Year("name", null));
	doNothing().when(groupDao).create(group);

	groupService.create(group);

	verify(groupDao).create(group);
    }

    @Test
    void givenGroup_whenUpdate_thanUpdeted() {
	Group group = new Group(1, "name", new Year("name", null));
	doNothing().when(groupDao).update(group);

	groupService.update(group);

	verify(groupDao).update(group);
    }

    @Test
    void givenGroupId_whenGetById_thenGot() {
	Group group = new Group(1, "name", new Year("name", null));
	List<Student> students = new ArrayList<>(Arrays.asList(new Student(1), new Student(2)));
	when(groupDao.findById(1)).thenReturn(group);
	when(studentDao.findByGroupId(1)).thenReturn(students);
	Group expected = new Group(1, "name", new Year("name", null));
	expected.setStudents(
		new ArrayList<>(Arrays.asList(new Student(1), new Student(2))));

	Group actual = groupService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenGroups_whenGetAll_thenGot() {
	List<Group> groups= new ArrayList<>(Arrays.asList(new Group(1, "name", new Year("name", null)),
		new Group(2, "name", new Year("name", null))));
	when(groupDao.findAll()).thenReturn(groups);
	when(studentDao.findByGroupId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Student(1);
		    case 2:
			return new Student(2);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Group> actual = groupService.getAll();

	List<Group> expected =  new ArrayList<>(Arrays.asList(new Group(1, "name", new Year("name", null)),
		new Group(2, "name", new Year("name", null))));
	expected.get(0).setStudents(new ArrayList<Student>(Arrays.asList(new Student(1))));
	expected.get(1).setStudents(new ArrayList<Student>(Arrays.asList(new Student(2))));
	assertEquals(expected, actual);
    }

    @Test
    void givenGroup_whenDelete_thenDeleted() {
	Group group = new Group(1, "name", new Year("name", null));
	doNothing().when(groupDao).deleteById(1);

	groupService.delete(group);

	verify(groupDao).deleteById(1);
    }

}
