package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.config.UniversityConfigProperties;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentDao studentDao;
    @Mock
    private GroupDao groupDao;
    @Mock
    private UniversityConfigProperties universityConfigProperties;

    @Test
    void givenStudent_whenCreate_thenCreated() {
	when(universityConfigProperties.getMaxGroupSize()).thenReturn(30);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	studentService.create(student);

	verify(studentDao).save(student);
    }

    @Test
    void givenStudentWithNotEnoughtGroupCapacity_whenCreate_thenThrowServiceException() {
	when(universityConfigProperties.getMaxGroupSize()).thenReturn(1);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	assertEquals("group capacity more then groupMaxSize=1",
		assertThrows(ServiceException.class, () -> studentService.create(student)).getMessage());

    }

    @Test
    void givenStudent_whenUpdate_thenUpdated() {
	when(universityConfigProperties.getMaxGroupSize()).thenReturn(30);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	studentService.create(student);

	verify(studentDao).save(student);
    }

    @Test
    void givenStudentWithNotEnoughtGroupCapacity_whenUpdate_thenThrowServiceException() {
	when(universityConfigProperties.getMaxGroupSize()).thenReturn(1);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	assertEquals("group capacity more then groupMaxSize=1",
		assertThrows(ServiceException.class, () -> studentService.create(student)).getMessage());

    }

    @Test
    void givenStudentId_whenGetById_thenGot() {
	Student student = createStudent();
	when(studentDao.findById(1)).thenReturn(Optional.of(student));

	Student actual = studentService.getById(1);

	Student expected = createStudent();
	assertEquals(expected, actual);
    }

    @Test
    void givenStudents_whenGetAll_thenGot() {
	List<Student> students = createStudents();
	when(studentDao.findAll()).thenReturn(students);

	List<Student> actual = studentService.getAll();

	List<Student> expected = createStudents();
	assertEquals(expected, actual);
    }

    @Test
    void givenStudentId_whenGetByGroup_thenGot() {
	List<Student> students = createStudents();
	Group group = Group.builder().id(1).build();
	when(studentDao.getByGroup(group)).thenReturn(students);

	List<Student> actual = studentService.getByGroup(group);

	List<Student> expected = createStudents();
	assertEquals(expected, actual);
    }

    @Test
    void givenStudent_whenDelete_thenDeleted() {
	Student student = createStudent();
	doNothing().when(studentDao).delete(student);

	studentService.delete(student);

	verify(studentDao).delete(student);
    }

    @Test
    void givenStudents_whenGetByPage_thenGot() {
	int pageNo = 0;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	List<Student> students = new ArrayList<>();
	students.add(createStudent());
	Page<Student> expected = new PageImpl<>(students, pageable, 3);
	when(studentDao.findAll(pageable)).thenReturn(expected);

	assertEquals(expected, studentService.getSelectedPage(pageable));
    }

    private Student createStudent() {
	return Student.builder().id(1).firstName("first name").lastName("last name").gender(Gender.MALE)
		.group(new Group.GroupBuilder().id(1).build()).build();
    }

    private List<Student> createStudents() {
	return new ArrayList<>(Arrays.asList(
		Student.builder().id(1).firstName("first name").lastName("last name").gender(Gender.MALE)
			.group(new Group.GroupBuilder().id(1).build()).build(),
		Student.builder().id(2).firstName("first name2").lastName("last name2").gender(Gender.MALE)
			.group(new Group.GroupBuilder().id(1).build()).build()));
    }

}
