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
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class TeacherServiceTest {

    @Mock
    private TeacherDao teacherDao;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    void givenTeacher_whenCereate_thanCreated() {
	Teacher teacher = new Teacher(1);
	doNothing().when(teacherDao).create(teacher);

	teacherService.create(teacher);

	verify(teacherDao).create(teacher);
    }

    @Test
    void givenTeacher_whenUpdate_thanUpdeted() {
	Teacher teacher = new Teacher(1);
	doNothing().when(teacherDao).update(teacher);

	teacherService.update(teacher);

	verify(teacherDao).update(teacher);
    }

    @Test
    void givenTeacherId_whenGetById_thenGot() {
	Teacher teacher = new Teacher(1);
	when(teacherDao.findById(1)).thenReturn(teacher);
	Teacher expected = new Teacher(1);

	Teacher actual = teacherService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenGetAll_thenGot() {
	List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(1)));
	when(teacherDao.findAll()).thenReturn(teachers);

	List<Teacher> actual = teacherService.getAll();

	List<Teacher> expected = new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(1)));
	assertEquals(expected, actual);
    }

    @Test
    void givenTeacher_whenDelete_thenDeleted() {
	Teacher teacher = new Teacher(1);
	doNothing().when(teacherDao).deleteById(1);

	teacherService.delete(teacher);

	verify(teacherDao).deleteById(1);
    }

}
