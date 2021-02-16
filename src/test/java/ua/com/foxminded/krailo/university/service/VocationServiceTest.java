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
    void givenVocationWithDurationFreeOfLessons_whenCereate_thenCreated() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(2));
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByVocationDate(vocation)).thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao).create(vocation);
    }

    @Test
    void givenVocationWithDurationWichHasLessons_whenCereate_thenNotCreated() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(1));
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByVocationDate(vocation)).thenReturn(lessons);

	vocationService.create(vocation);

	verify(vocationDao, never()).create(vocation);
    }

    @Test
    void givenVocationWithDurationFreeOfLessons_whenUpdete__thenUpdeted() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(2));
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByVocationDate(vocation)).thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao).update(vocation);
    }

    @Test
    void givenVocationWithDurationWichHasLessons_whenUpdete__thenNotUpdeted() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(1));
	List<Lesson> lessons = createLessons();
	when(lessonDao.findByVocationDate(vocation)).thenReturn(lessons);

	vocationService.update(vocation);

	verify(vocationDao, never()).update(vocation);
    }

    @Test
    void givenVocationId_whenGetById_thenGot() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(1));
	when(vocationDao.findById(1)).thenReturn(vocation);
	Vocation expected = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(1));

	Vocation actual = vocationService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenVocations_whenGetAll_thenGot() {
	List<Vocation> vocations = new ArrayList<>(Arrays.asList(
		new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
			LocalDate.of(2021, 01, 04), new Teacher(1)),
		new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
			LocalDate.of(2021, 01, 04), new Teacher(1))));
	when(vocationDao.findAll()).thenReturn(vocations);

	List<Vocation> actual = vocationService.getAll();

	List<Vocation> expected = new ArrayList<>(Arrays.asList(
		new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
			LocalDate.of(2021, 01, 04), new Teacher(1)),
		new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
			LocalDate.of(2021, 01, 04), new Teacher(1))));
	assertEquals(expected, actual);
    }

    @Test
    void givenVocation_whenDelete_thenDeleted() {
	Vocation vocation = new Vocation(1, "kind", LocalDate.of(2020, 12, 31), LocalDate.of(2021, 01, 01),
		LocalDate.of(2021, 01, 04), new Teacher(1));
	doNothing().when(vocationDao).deleteById(1);

	vocationService.delete(vocation);

	verify(vocationDao).deleteById(1);
    }

    private List<Lesson> createLessons() {
	List<Lesson> lessons = new ArrayList<>();
	lessons.add(new Lesson(LocalDate.of(2021, 01, 01), new LessonTime(1), new Subject(1, null), new Audience(1),
		new Teacher(1), new Timetable(1)));
	lessons.add(new Lesson(LocalDate.of(2021, 01, 01), new LessonTime(1), new Subject(1, null), new Audience(1),
		new Teacher(1), new Timetable(1)));
	return lessons;
    }
}
