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
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class SubjectServiceTest {

    @Mock
    private SubjectDao subjectDao;
    @Mock
    private TeacherDao teacherDao;
    @InjectMocks
    private SubjectService subjectService;

    @Test
    void givenSubject_whenCereate_thanCreated() {
	Subject subject = new Subject("name");
	doNothing().when(subjectDao).create(subject);

	subjectService.create(subject);

	verify(subjectDao).create(subject);
    }

    @Test
    void givenSubject_whenUpdate_thanUpdeted() {
	Subject subject = new Subject(1, "name");
	doNothing().when(subjectDao).update(subject);

	subjectService.update(subject);

	verify(subjectDao).update(subject);
    }

    @Test
    void givenSubjectId_whenGetById_thenGot() {
	Subject subject = new Subject(1, "name");
	List<Teacher> teachers = new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(2)));
	when(subjectDao.findById(1)).thenReturn(subject);
	when(teacherDao.findBySubjectId(1)).thenReturn(teachers);
	Subject expected = new Subject(1, "name");
	expected.setTeachers(new ArrayList<>(Arrays.asList(new Teacher(1), new Teacher(2))));

	Subject actual = subjectService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenSubjects_whenGetAll_thenGot() {
	List<Subject> subjects = new ArrayList<>(Arrays.asList(new Subject(1, "name"), new Subject(2, "name")));
	when(subjectDao.findAll()).thenReturn(subjects);
	when(teacherDao.findBySubjectId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Teacher(1);
		    case 2:
			return new Teacher(2);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Subject> actual = subjectService.getAll();

	List<Subject> expected = new ArrayList<>(Arrays.asList(new Subject(1, "name"), new Subject(2, "name")));
	expected.get(0).setTeachers(new ArrayList<Teacher>(Arrays.asList(new Teacher(1))));
	expected.get(1).setTeachers(new ArrayList<Teacher>(Arrays.asList(new Teacher(2))));
	assertEquals(expected, actual);
    }

    @Test
    void givenSubject_whenDelete_thenDeleted() {
	Subject subject = new Subject(1, "name");
	doNothing().when(subjectDao).deleteById(1);

	subjectService.delete(subject);

	verify(subjectDao).deleteById(1);
    }

}
