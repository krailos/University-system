package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import ua.com.foxminded.krailo.university.dao.DepartmentDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Department;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentDao departmentDao;
    @Mock
    private TeacherDao teacherDao;
    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void givenDepartment_whenCereate_thanCreated() {
	Department department = Department.builder().id(1).name("name").build();
	doNothing().when(departmentDao).create(department);

	departmentService.create(department);

	verify(departmentDao).create(department);
    }

    @Test
    void givenDepartment_whenUpdate_thanUpdeted() {
	Department department =Department.builder().id(1).name("name").build();
	doNothing().when(departmentDao).update(department);

	departmentService.update(department);

	verify(departmentDao).update(department);
    }

    @Test
    void givenDepartmentId_whenGetById_thenGot() {
	Department department =  Department.builder().id(1).name("name").build();
	when(departmentDao.findById(1)).thenReturn(department);

	Department actual = departmentService.getById(1);

	Department expected = Department.builder().id(1).name("name").build();
	assertEquals(expected, actual);
    }

    @Test
    void givenDepartments_whenGetAll_thenGot() {
	List<Department> departments = new ArrayList<>(
		Arrays.asList(Department.builder().id(1).name("name").build(),
			 Department.builder().id(2).name("name2").build()));
	when(departmentDao.findAll()).thenReturn(departments);

	List<Department> actual = departmentService.getAll();

	List<Department> expected = new ArrayList<>(
		Arrays.asList(Department.builder().id(1).name("name").build(),
			Department.builder().id(2).name("name2").build()));
	assertEquals(expected, actual);
    }

    @Test
    void givenDepartment_whenDelete_thenDeleted() {
	Department department = new Department.DepartmentBuilder().id(1).name("name").build();
	doNothing().when(departmentDao).deleteById(1);

	departmentService.delete(department);

	verify(departmentDao).deleteById(1);
    }

}
