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
import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Department;
import ua.com.foxminded.krailo.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentDao departmentDao;
    @Mock
    private TeacherDao teacherDao;
    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void givenDepartment_whenCereate_thanCreated() {
	Department department = new Department(1, "name");
	doNothing().when(departmentDao).create(department);

	departmentService.create(department);

	verify(departmentDao).create(department);
    }

    @Test
    void givenDepartment_whenUpdate_thanUpdeted() {
	Department department = new Department(1, "name");
	doNothing().when(departmentDao).update(department);

	departmentService.update(department);

	verify(departmentDao).update(department);
    }

    @Test
    void givenDepartmentId_whenGetById_thenGot() {
	Department department = new Department(1, "name");
	List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(2)));
	when(departmentDao.findById(1)).thenReturn(department);
	when(teacherDao.findByDepartmentId(1)).thenReturn(teachers);
	Department expected = new Department(1, "name");
	expected.setTeachers(new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(2))));

	Department actual = departmentService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenDepartments_whenGetAll_thenGot() {
	List<Department> departments = new ArrayList<>(
		Arrays.asList(new Department(1, "name"),new Department(2, "name")));
	when(departmentDao.findAll()).thenReturn(departments);
	when(teacherDao.findByDepartmentId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Teacher(1);
		    case 2:
			return new Teacher(1);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Department> actual = departmentService.getAll();

	List<Department> expected = new ArrayList<>(
		Arrays.asList(new Department(1, "name"),new Department(2, "name")));
	expected.get(0).setTeachers(new ArrayList<Teacher>(Arrays.asList(new Teacher(1))));
	expected.get(1).setTeachers(new ArrayList<Teacher>(Arrays.asList(new Teacher(1))));
	assertEquals(expected, actual);
    }

    @Test
    void givenDepartment_whenDelete_thenDeleted() {
	Department department = new Department(1, "name");
	doNothing().when(departmentDao).deleteById(1);

	departmentService.delete(department);

	verify(departmentDao).deleteById(1);
    }

}
