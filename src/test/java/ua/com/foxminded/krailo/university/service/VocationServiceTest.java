package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Vocation;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class VocationServiceTest {

    @Mock
    private VocationDao vocationDao;
    @Mock
    private LessonDao lessonDao;
    @InjectMocks
    private VocationService vocationService;

    @Test
    void givenVocationPeriodWhichNotMatchWithLessons_whenCereate_thenCreated() {
	Vocation vocation = createVocation();
	List<Lesson> lessons =  new ArrayList<>();;
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao).create(vocation);
    }

    @Test
    void givenVocationPeriodWhichMatchWithLessons_whenCereate_thenNotCreated() {
	Vocation vocation = createVocation();
	List<Lesson> lessons = createLessons();
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationWithEndLessThenStart_whenCereate_thenNotCreated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 01, 02));
	vocation.setEnd(LocalDate.of(2021, 01, 01));
	List<Lesson> lessons = new ArrayList<>();
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationPeriodWhichNotMatchWithLessons_whenUpdate_thenUpdated() {
	Vocation vocation = createVocation();
	List<Lesson> lessons = new ArrayList<>();
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao).update(vocation);
    }

    @Test
    void givenVocationPeriodWhichMatchWithLessons_whenUpdate_thenNotUpdated() {
	Vocation vocation = createVocation();
	List<Lesson> lessons = createLessons();
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao, never()).update(vocation);
    }

    @Test
    void givenVocationWithEndLessThenStart_whenUpdate_thenNotUpdated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 01, 02));
	vocation.setEnd(LocalDate.of(2021, 01, 01));
	List<Lesson> lessons = new ArrayList<>();
	when(lessonDao.findBetweenVocationStartEndAndTeacherId(vocation)).thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao, never()).update(vocation);
    }

    @Test
    void givenVocationId_whenGetById_thenGot() {
	Vocation vocation = createVocation();
	when(vocationDao.findById(1)).thenReturn(vocation);
	Vocation expected = createVocation();

	Vocation actual = vocationService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenVocations_whenGetAll_thenGot() {
	List<Vocation> vocations = createVocations();
	when(vocationDao.findAll()).thenReturn(vocations);

	List<Vocation> actual = vocationService.getAll();

	List<Vocation> expected = createVocations();
	assertEquals(expected, actual);
    }

    @Test
    void givenVocation_whenDelete_thenDeleted() {
	Vocation vocation = createVocation();
	doNothing().when(vocationDao).deleteById(1);

	vocationService.delete(vocation);

	verify(vocationDao).deleteById(1);
    }

    private List<Lesson> createLessons() {
	List<Lesson> lessons = new ArrayList<>();
	lessons.add(new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 01))
		.withTeacher(new Teacher.TeacherBuilder().withId(1).withFirstName("teacher").built()).built());
	lessons.add(new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 02))
		.withTeacher(new Teacher.TeacherBuilder().withId(1).withFirstName("teacher").built()).built());
	lessons.add(new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 03))
		.withTeacher(new Teacher.TeacherBuilder().withId(1).withFirstName("teacher").built()).built());

	return lessons;
    }

    private Vocation createVocation() {
	return new Vocation.VocationBuilder().withId(1).withKind("kind").withApplyingDate(LocalDate.of(2020, 12, 31))
		.withStartDate(LocalDate.of(2021, 01, 01)).withEndDate(LocalDate.of(2021, 01, 07))
		.withTeacher(new Teacher.TeacherBuilder().withId(1).withFirstName("teacher").built()).built();
    }

    private List<Vocation> createVocations() {
	return new ArrayList<>(Arrays.asList(
		new Vocation.VocationBuilder().withId(1).withKind("kind").withApplyingDate(LocalDate.of(2020, 12, 31))
			.withStartDate(LocalDate.of(2021, 01, 01)).withEndDate(LocalDate.of(2021, 01, 07))
			.withTeacher(new Teacher.TeacherBuilder().withId(1).withFirstName("teacher").built()).built(),
		new Vocation.VocationBuilder().withId(2).withKind("kind").withApplyingDate(LocalDate.of(2020, 12, 31))
			.withStartDate(LocalDate.of(2021, 01, 01)).withEndDate(LocalDate.of(2021, 01, 07))
			.withTeacher(new Teacher.TeacherBuilder().withId(2).withFirstName("teacher2").built())
			.built()));

    }
}
