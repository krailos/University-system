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
import org.springframework.test.util.ReflectionTestUtils;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@ExtendWith(MockitoExtension.class)
class VocationServiceTest {

    @Mock
    private VocationDao vocationDao;
    @Mock
    private LessonDao lessonDao;
    @Mock
    private HolidayDao holidayDao;
    @InjectMocks
    private VocationService vocationService;

    @Test
    void givenVocationAllCorrectData_whenCereate_thenCreated() {
	ReflectionTestUtils.setField(vocationService, "vocationDurationGeneral", 14);
	Vocation vocation = createVocation();	

	vocationService.create(vocation);

	verify(vocationDao).create(vocation);
    }

    @Test
    void givenVocationPeriodDurationMoreThenMaxDuration_whenCereate_thenNotCreated() {
	ReflectionTestUtils.setField(vocationService, "vocationDurationGeneral", 14);
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 10, 01));
	vocation.setEnd(LocalDate.of(2021, 10, 25));	

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationPeriodWithLessons_whenCereate_thenNotCreated() {
	ReflectionTestUtils.setField(vocationService, "vocationDurationGeneral", 14);
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 01, 01));
	vocation.setEnd(LocalDate.of(2021, 01, 03));
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd()))
		.thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationWithEndLessThenStart_whenCereate_thenNotCreated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 01, 02));
	vocation.setEnd(LocalDate.of(2021, 01, 01));

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationWithStartAndEndDateWIthDifrentYear_whenCreate_thenNotCreated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 12, 31));
	vocation.setEnd(LocalDate.of(2022, 01, 10));

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationPeriodWhithoutLessons_whenUpdate_thenUpdated() {
	Vocation vocation = createVocation();
	when(lessonDao.findByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd()))
		.thenReturn(new ArrayList<>());

	vocationService.update(vocation);

	verify(vocationDao).update(vocation);
    }

    @Test
    void givenVocationPeriodWithLessons_whenUpdate_thenNotUpdated() {
	Vocation vocation = createVocation();
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd()))
		.thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao, never()).update(vocation);
    }

    @Test
    void givenVocationWithEndDateLessThenStart_whenUpdate_thenNotUpdated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 01, 02));
	vocation.setEnd(LocalDate.of(2021, 01, 01));

	vocationService.update(vocation);

	verify(vocationDao, never()).update(vocation);
    }

    @Test
    void givenVocationWithStartAndEndDateWIthDifrentYear_whenUpdate_thenNotUpdated() {
	Vocation vocation = createVocation();
	vocation.setStart(LocalDate.of(2021, 12, 31));
	vocation.setEnd(LocalDate.of(2022, 01, 10));

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
	lessons.add(Lesson.builder().id(1).date(LocalDate.of(2021, 01, 01))
		.teacher(Teacher.builder().id(1).firstName("teacher").build()).build());
	lessons.add(Lesson.builder().id(1).date(LocalDate.of(2021, 01, 02))
		.teacher(Teacher.builder().id(1).firstName("teacher").build()).build());
	lessons.add(Lesson.builder().id(1).date(LocalDate.of(2021, 01, 03))
		.teacher(Teacher.builder().id(1).firstName("teacher").build()).build());

	return lessons;
    }

    private Vocation createVocation() {
	return Vocation.builder().id(1).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2020, 12, 31))
		.startDate(LocalDate.of(2021, 10, 01)).endDate(LocalDate.of(2021, 10, 05))
		.teacher(Teacher.builder().id(1).firstName("teacher").build()).build();
    }

    private List<Vocation> createVocations() {
	return new ArrayList<>(Arrays.asList(
		Vocation.builder().id(1).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2020, 12, 31))
			.startDate(LocalDate.of(2021, 01, 01)).endDate(LocalDate.of(2021, 01, 05))
			.teacher(Teacher.builder().id(1).firstName("teacher").build()).build(),
		Vocation.builder().id(2).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2020, 12, 31))
			.startDate(LocalDate.of(2021, 01, 01)).endDate(LocalDate.of(2021, 01, 05))
			.teacher(Teacher.builder().id(2).firstName("teacher2").build()).build()));

    }
}
