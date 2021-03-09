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
import org.springframework.test.util.ReflectionTestUtils;

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

   
    @Test
    void givenStudent_whenCreate_thenCreated() {
	ReflectionTestUtils.setField(studentService, "groupMaxCapacity", 30);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	studentService.create(student);

	verify(studentDao).create(student);
    }

    @Test
    void givenStudentWithNotEnoughtGroupCapacity_whenCreate_thenThrowServiceException(){
	ReflectionTestUtils.setField(studentService, "groupMaxCapacity", 1);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	assertThrows(ServiceException.class,() -> studentService.create(student));
	
    }

    @Test
    void givenStudent_whenUpdate_thenUpdated() {
	ReflectionTestUtils.setField(studentService, "groupMaxCapacity", 30);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	studentService.update(student);

	verify(studentDao).update(student);
    }

    @Test
    void givenStudentWithNotEnoughtGroupCapacity_whenUpdate_thenThrowServiceException(){
	ReflectionTestUtils.setField(studentService, "groupMaxCapacity", 1);
	Student student = createStudent();
	Group group = Group.builder().id(1).build();
	group.setStudents(new ArrayList<>(createStudents()));
	when(groupDao.findById(student.getGroup().getId())).thenReturn(Optional.of(group));

	assertThrows(ServiceException.class,() -> studentService.update(student));

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
    void givenStudentId_whenGetByGroupId_thenGot() {
	List<Student> students = createStudents();
	when(studentDao.findByGroupId(1)).thenReturn(students);

	List<Student> actual = studentService.getByGroupId(1);

	List<Student> expected = createStudents();
	assertEquals(expected, actual);
    }

    @Test
    void givenStudent_whenDelete_thenDeleted() {
	Student student = createStudent();
	doNothing().when(studentDao).deleteById(1);

	studentService.delete(student);

	verify(studentDao).deleteById(1);
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
