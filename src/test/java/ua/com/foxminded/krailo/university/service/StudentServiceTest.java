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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentDao studentDao;

    @Test
    void givenStudent_whenCreate_thenCreated() {
	Student student = createStudent();
	doNothing().when(studentDao).create(student);

	studentService.create(student);

	verify(studentDao).create(student);
    }

    @Test
    void givenStudent_whenUpdate_thenUpdated() {
	Student student = createStudent();
	doNothing().when(studentDao).update(student);

	studentService.update(student);

	verify(studentDao).update(student);
    }

    @Test
    void givenStudentId_whenGetById_thenGot() {
	Student student = createStudent();
	when(studentDao.findById(1)).thenReturn(student);

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
	return new Student.StudentBuilder().withId(1).withFirstName("first name").withlastName("last name")
		.withGender(Gender.MALE).withGroup(new Group.GroupBuilder().withId(1).built()).built();
    }

    private List<Student> createStudents() {
	return new ArrayList<>(Arrays.asList(
		new Student.StudentBuilder().withId(1).withFirstName("first name").withlastName("last name")
			.withGender(Gender.MALE).withGroup(new Group.GroupBuilder().withId(1).built()).built(),
		new Student.StudentBuilder().withId(2).withFirstName("first name2").withlastName("last name2")
			.withGender(Gender.MALE).withGroup(new Group.GroupBuilder().withId(1).built()).built()));
    }

}
