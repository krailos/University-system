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
import ua.com.foxminded.krailo.university.dao.SubjectDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Subject;

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
	Subject subject = createSubject();
	doNothing().when(subjectDao).create(subject);

	subjectService.create(subject);

	verify(subjectDao).create(subject);
    }

    @Test
    void givenSubject_whenUpdate_thanUpdeted() {
	Subject subject = createSubject();
	doNothing().when(subjectDao).update(subject);

	subjectService.update(subject);

	verify(subjectDao).update(subject);
    }

    @Test
    void givenSubjectId_whenGetById_thenGot() {
	Subject subject = createSubject();
	when(subjectDao.findById(1)).thenReturn(subject);
	Subject expected = createSubject();

	Subject actual = subjectService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenSubjects_whenGetAll_thenGot() {
	List<Subject> subjects = createSubjects();
	when(subjectDao.findAll()).thenReturn(subjects);

	List<Subject> actual = subjectService.getAll();

	List<Subject> expected = createSubjects();
	assertEquals(expected, actual);
    }

    @Test
    void givenSubject_whenDelete_thenDeleted() {
	Subject subject = createSubject();
	doNothing().when(subjectDao).deleteById(1);

	subjectService.delete(subject);

	verify(subjectDao).deleteById(1);
    }

    private Subject createSubject() {
	return new Subject.SubjectBuilder().withId(1).withName("name").built();
    }

    private List<Subject> createSubjects() {
	return new ArrayList<>(Arrays.asList(new Subject.SubjectBuilder().withId(1).withName("name").built(),
		new Subject.SubjectBuilder().withId(2).withName("name2").built()));
    }

}
