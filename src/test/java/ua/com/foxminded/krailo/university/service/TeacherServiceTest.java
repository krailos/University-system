package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherDao teacherDao;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    void givenTeacher_whenCereate_thanCreated() {
	Teacher teacher = createTeacher();
	doNothing().when(teacherDao).create(teacher);

	teacherService.create(teacher);

	verify(teacherDao).create(teacher);
    }

    @Test
    void givenTeacher_whenUpdate_thanUpdeted() {
	Teacher teacher = createTeacher();
	doNothing().when(teacherDao).update(teacher);

	teacherService.update(teacher);

	verify(teacherDao).update(teacher);
    }

    @Test
    void givenTeacherId_whenGetById_thenGot() {
	Teacher teacher = createTeacher();
	when(teacherDao.findById(1)).thenReturn(Optional.of(teacher));
	Teacher expected = createTeacher();

	Teacher actual = teacherService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenGetAll_thenGot() {
	List<Teacher> teachers = createTeachers();
	when(teacherDao.findAll()).thenReturn(teachers);

	List<Teacher> actual = teacherService.getAll();

	List<Teacher> expected = createTeachers();
	assertEquals(expected, actual);
    }

    @Test
    void givenTeacher_whenDelete_thenDeleted() {
	Teacher teacher = createTeacher();
	doNothing().when(teacherDao).deleteById(1);

	teacherService.delete(teacher);

	verify(teacherDao).deleteById(1);
    }

    private Teacher createTeacher() {
	return Teacher.builder().id(1).firstName("first name").lastName("last name").gender(Gender.MALE).build();
    }

    private List<Teacher> createTeachers() {
	return new ArrayList<>(Arrays.asList(
		Teacher.builder().id(1).firstName("first name").lastName("last name").gender(Gender.MALE).build(),
		Teacher.builder().id(2).firstName("first name 2").lastName("last name 2").gender(Gender.MALE).build()));
    }

}
