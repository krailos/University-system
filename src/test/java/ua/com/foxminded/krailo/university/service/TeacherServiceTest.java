package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import ua.com.foxminded.krailo.university.exception.NoTeachersForSubstitute;
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
	when(lessonDao.findByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);
	when(teacherDao.findBySubjectId(subject.getId())).thenReturn(teachersTeachesTheSameLessons);
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), newTeacher.getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.empty());

	List<Teacher> teachersForSubstitute = teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate,
		finishDate);

	assertEquals(Arrays.asList(newTeacher), teachersForSubstitute);
    }

    @Test
    void givenNotFreeTeacher_whenfindTeacherForSubstitute_thenNoTeachersForSubstituteExceptionThrown() {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Subject subject = Subject.builder().id(1).name("rightLesson").build();
	Teacher subsitutedTeacher = Teacher.builder().id(1).firstName("substitutedTeacher").build();
	Teacher newTeacher = Teacher.builder().id(2).firstName("newTeacher").subjects(Arrays.asList(subject)).build();
	List<Teacher> teachersTeachesTheSameLessons = Arrays.asList(newTeacher);
	Lesson lesson = Lesson.builder().id(1).date(startDate).subject(subject)
		.lessonTime(LessonTime.builder().id(1).orderNumber("first").build()).teacher(subsitutedTeacher).build();
	List<Lesson> substitutedlessons = Arrays.asList(lesson);
	when(teacherDao.findBySubjectId(subject.getId())).thenReturn(teachersTeachesTheSameLessons);
	when(lessonDao.findByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), newTeacher.getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(2).build()));

	Exception exception = assertThrows(NoTeachersForSubstitute.class,
		() -> teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate, finishDate));

	assertEquals("there is no free teachers", exception.getMessage());
    }

    @Test
    void givenTeacherNotTeachLesson_whenfindTeacherForSubstitute_thenNoTeachersForSubstituteExceptionThrown() {
	LocalDate startDate = LocalDate.now();
	LocalDate finishDate = LocalDate.now().plusWeeks(1);
	Subject rightSubject = Subject.builder().id(1).name("rightLesson").build();
	Teacher subsitutedTeacher = Teacher.builder().id(1).firstName("substitutedTeacher")
		.subjects(Arrays.asList(rightSubject)).build();
	Lesson lesson = Lesson.builder().id(1).date(startDate).subject(rightSubject)
		.lessonTime(LessonTime.builder().id(1).orderNumber("first").build()).teacher(subsitutedTeacher).build();
	List<Lesson> substitutedlessons = Arrays.asList(lesson);
	when(lessonDao.findByTeacherBetweenDates(subsitutedTeacher, startDate, finishDate))
		.thenReturn(substitutedlessons);

	Exception exception = assertThrows(NoTeachersForSubstitute.class,
		() -> teacherService.findTeachersForSubstitute(subsitutedTeacher, startDate, finishDate));

	assertEquals("there is no free teachers", exception.getMessage());
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
