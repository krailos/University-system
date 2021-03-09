package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;

@ExtendWith(MockitoExtension.class)
class TimetableServiceTest {

    @Mock
    private TimetableDao timetableDao;
    @Mock
    private LessonDao lessonDao;
    @InjectMocks
    private TimetableService timetableService;

    @Test
    void givenUniqueTimetable_whenCereate_thanCreated() {
	Timetable timetable = createTimetable();
	doNothing().when(timetableDao).create(timetable);

	timetableService.create(timetable);

	verify(timetableDao).create(timetable);
    }

    @Test
    void givenTimetable_whenUpdate_thanUpdated() {
	Timetable timetable = createTimetable();
	doNothing().when(timetableDao).update(timetable);

	timetableService.update(timetable);

	verify(timetableDao).update(timetable);
    }

    @Test
    void givenTimetableId_whenGetById_thenGot() {
	Timetable timetable = createTimetable();
	when(timetableDao.findById(1)).thenReturn(Optional.of(timetable));
	Timetable expected = createTimetable();

	Timetable actual = timetableService.getById(1);
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetables_whenGetAll_thenGot() {
	List<Timetable> timetables = createTimetables();
	when(timetableDao.findAll()).thenReturn(timetables);

	List<Timetable> actual = timetableService.getAll();

	List<Timetable> expected = createTimetables();
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetable_whenDelete_thenDeleted() {
	Timetable timetable = createTimetable();
	doNothing().when(timetableDao).deleteById(1);

	timetableService.delete(timetable);

	verify(timetableDao).deleteById(1);
    }

    @Test
    void givenTimetableIdAndTeacherAndDate_whenGetTimetableForTeacherByDate_thenGot() {
	Teacher teacher = Teacher.builder().id(1).build();
	Timetable timetable = Timetable.builder().name("for teacher by date").build();
	List<Lesson> lessons = new ArrayList<>(
		Arrays.asList(Lesson.builder().id(1).build(), Lesson.builder().id(2).build()));
	when(timetableDao.findById(1)).thenReturn(Optional.of(timetable));
	when(lessonDao.findByTeacherAndDate(teacher, LocalDate.of(2021, 01, 02))).thenReturn(lessons);

	Timetable actual = timetableService.getTimetableForTeacherByDate(1, teacher, LocalDate.of(2021, 01, 02));

	Timetable expected = Timetable.builder().name("for teacher by date").build();
	expected.setTeacher(teacher);
	expected.setLessons(lessons);
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetableIdAndTeacherAndDate_whenGetTimetableForTeacherByMonth_thenGot() {
	Teacher teacher = Teacher.builder().id(1).build();
	Timetable timetable = Timetable.builder().name("for teacher by date").build();
	List<Lesson> lessons = new ArrayList<>(
		Arrays.asList(Lesson.builder().id(1).build(), Lesson.builder().id(2).build()));
	when(timetableDao.findById(1)).thenReturn(Optional.of(timetable));
	when(lessonDao.findByTeacherBetweenDates(teacher, LocalDate.of(2021, 01, 02),
		LocalDate.of(2021, 01, 02).plusMonths(1))).thenReturn(lessons);

	Timetable actual = timetableService.getTimetableForTeacherByMonth(1, teacher, LocalDate.of(2021, 01, 02));

	Timetable expected = Timetable.builder().name("for teacher by date").build();
	expected.setTeacher(teacher);
	expected.setLessons(lessons);
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetableIdAndStudentAndDate_whenGetTimetableForStudentByDate_thenGot() {
	Student student = Student.builder().id(1).build();
	Timetable timetable = Timetable.builder().name("for teacher by date").build();
	List<Lesson> lessons = new ArrayList<>(
		Arrays.asList(Lesson.builder().id(1).build(), Lesson.builder().id(2).build()));
	when(timetableDao.findById(1)).thenReturn(Optional.of(timetable));
	when(lessonDao.findByStudentAndDate(student, LocalDate.of(2021, 01, 02))).thenReturn(lessons);

	Timetable actual = timetableService.getTimetableForStudentByDate(1, student, LocalDate.of(2021, 01, 02));

	Timetable expected = Timetable.builder().name("for teacher by date").build();
	expected.setStudent(student);
	expected.setLessons(lessons);
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetableIdAndStudentAndDate_whenGetTimetableForStudentByMonth_thenGot() {
	Student student = Student.builder().id(1).build();
	Timetable timetable = Timetable.builder().name("for teacher by date").build();
	List<Lesson> lessons = new ArrayList<>(
		Arrays.asList(Lesson.builder().id(1).build(), Lesson.builder().id(2).build()));
	when(timetableDao.findById(1)).thenReturn(Optional.of(timetable));
	when(lessonDao.findByStudentBetweenDates(student, LocalDate.of(2021, 01, 02),
		LocalDate.of(2021, 01, 02).plusMonths(1))).thenReturn(lessons);

	Timetable actual = timetableService.getTimetableForStudentByMonth(1, student, LocalDate.of(2021, 01, 02));

	Timetable expected = Timetable.builder().name("for teacher by date").build();
	expected.setStudent(student);
	expected.setLessons(lessons);
	assertEquals(expected, actual);
    }

    private Timetable createTimetable() {
	return Timetable.builder().id(1).name("name").build();
    }

    private List<Timetable> createTimetables() {
	return new ArrayList<>(Arrays.asList(Timetable.builder().id(1).name("name").build(),
		Timetable.builder().id(1).name("name").build()));
    }

}
