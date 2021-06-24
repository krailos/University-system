package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherDao teacherDao;
    @Mock
    private LessonDao lessonDao;
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
	when(teacherDao.getById(1)).thenReturn(Optional.of(teacher));
	Teacher expected = createTeacher();

	Teacher actual = teacherService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenTeachers_whenGetAll_thenGot() {
	List<Teacher> teachers = createTeachers();
	when(teacherDao.getAll()).thenReturn(teachers);

	List<Teacher> actual = teacherService.getAll();

	List<Teacher> expected = createTeachers();
	assertEquals(expected, actual);
    }

    @Test
    void givenTeacher_whenDelete_thenDeleted() {
	Teacher teacher = createTeacher();
	doNothing().when(teacherDao).delete(teacher);

	teacherService.delete(teacher);

	verify(teacherDao).delete(teacher);
    }

    @Test
    void givenFreeTeacher_whenfindTeacherForSubstitute_thenFound() {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Subject subject = Subject.builder().id(1).name("rightLesson").build();
	Teacher subsitutedTeacher = Teacher.builder().id(1).firstName("substitutedTeacher")
		.subjects(Arrays.asList(subject)).build();
	Teacher newTeacher = Teacher.builder().id(2).firstName("newTeacher").subjects(Arrays.asList(subject)).build();
	List<Teacher> teachersTeachesTheSameLessons = Arrays.asList(newTeacher);
	Lesson lesson = Lesson.builder().id(1).date(startDate).subject(subject)
		.lessonTime(LessonTime.builder().id(1).orderNumber("first").build()).teacher(subsitutedTeacher).build();
	List<Lesson> substitutedlessons = Arrays.asList(lesson);
	when(lessonDao.getByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);
	when(teacherDao.getBySubject(subject)).thenReturn(teachersTeachesTheSameLessons);
	when(lessonDao.getByDateAndTeacherAndLessonTime(lesson.getDate(), newTeacher, lesson.getLessonTime()))
		.thenReturn(Optional.empty());

	List<Teacher> teachersForSubstitute = teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate,
		finishDate);

	assertEquals(Arrays.asList(newTeacher), teachersForSubstitute);
    }

    @Test
    void givenNotFreeTeacher_whenfindTeacherForSubstitute_thenEmptyListOfTeachersReturned() {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Subject subject = Subject.builder().id(1).name("rightLesson").build();
	Teacher subsitutedTeacher = Teacher.builder().id(1).firstName("substitutedTeacher").build();
	Teacher newTeacher = Teacher.builder().id(2).firstName("newTeacher").subjects(Arrays.asList(subject)).build();
	List<Teacher> teachersTeachesTheSameLessons = Arrays.asList(newTeacher);
	Lesson lesson = Lesson.builder().id(1).date(startDate).subject(subject)
		.lessonTime(LessonTime.builder().id(1).orderNumber("first").build()).teacher(subsitutedTeacher).build();
	List<Lesson> substitutedlessons = Arrays.asList(lesson);
	when(teacherDao.getBySubject(subject)).thenReturn(teachersTeachesTheSameLessons);
	when(lessonDao.getByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);
	when(lessonDao.getByDateAndTeacherAndLessonTime(lesson.getDate(), newTeacher, lesson.getLessonTime()))
		.thenReturn(Optional.of(Lesson.builder().id(2).build()));

	List<Teacher> teachers = teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate, finishDate);

	assertTrue(teachers.isEmpty());
    }

    @Test
    void givenTeacherNotTeachLesson_whenfindTeacherForSubstitute_thenEmptyListOfTeachersReturned() {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Subject rightSubject = Subject.builder().id(1).name("rightLesson").build();
	Teacher subsitutedTeacher = Teacher.builder().id(1).firstName("substitutedTeacher")
		.subjects(Arrays.asList(rightSubject)).build();
	Lesson lesson = Lesson.builder().id(1).date(startDate).subject(rightSubject)
		.lessonTime(LessonTime.builder().id(1).orderNumber("first").build()).teacher(subsitutedTeacher).build();
	List<Lesson> substitutedlessons = Arrays.asList(lesson);
	when(lessonDao.getByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);

	List<Teacher> teachers = teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate, finishDate);

	assertTrue(teachers.isEmpty());
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
