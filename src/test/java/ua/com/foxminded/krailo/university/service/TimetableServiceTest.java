package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class TimetableServiceTest {

    @Mock
    private TimetableDao timetableDao;
    @Mock
    private LessonDao lessonDao;
    @InjectMocks
    private TimetableService timetableService;

    @Test
    void givenUniqueTimetable_whenCereate_thanCreated() {
	List<Timetable> timetables = createTimetables();
	Timetable timetable = new Timetable(1, "new name", new Year(1, "first", new Speciality(1, "first", null)));
	when(timetableDao.findByYear(timetable)).thenReturn(timetables);

	timetableService.create(timetable);

	verify(timetableDao).create(timetable);
    }

    @Test
    void givenNotUniqTimetableWith_whenCereate_thanCreated() {
	List<Timetable> timetables = createTimetables();
	Timetable timetable = new Timetable(1, "main timetable",
		new Year(1, "first", new Speciality(1, "first", null)));
	when(timetableDao.findByYear(timetable)).thenReturn(timetables);

	timetableService.create(timetable);

	verify(timetableDao, never()).create(timetable);
    }

    @Test
    void givenUniqueTimetable_whenUpdate_thanUpdated() {
	List<Timetable> timetables = createTimetables();
	Timetable timetable = new Timetable(1, "new name", new Year(1, "first", new Speciality(1, "first", null)));
	when(timetableDao.findByYear(timetable)).thenReturn(timetables);

	timetableService.update(timetable);

	verify(timetableDao).update(timetable);
    }

    @Test
    void givenNotUniqTimetableWith_whenUpdate_thanUpdated() {
	List<Timetable> timetables = createTimetables();
	Timetable timetable = new Timetable(1, "main timetable",
		new Year(1, "first", new Speciality(1, "first", null)));
	when(timetableDao.findByYear(timetable)).thenReturn(timetables);

	timetableService.update(timetable);

	verify(timetableDao, never()).update(timetable);
    }

    @Test
    void givenTimetableId_whenGetById_thenGot() {
	Timetable timetable = new Timetable(1, "main timetable",
		new Year(1, "first", new Speciality(1, "first", null)));
	List<Lesson> lessons = new ArrayList<Lesson>(Arrays.asList(new Lesson(1)));
	when(timetableDao.findById(1)).thenReturn(timetable);
	when(lessonDao.findByTimetableId(1)).thenReturn(lessons);
	Timetable expected = new Timetable(1, "main timetable", new Year(1, "first", new Speciality(1, "first", null)),
		lessons);

	Timetable actual = timetableService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenTimetables_whenGetAll_thenGot() {
	List<Timetable> timetables = createTimetables();
	when(timetableDao.findAll()).thenReturn(timetables);
	when(lessonDao.findByTimetableId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Lesson(1);
		    case 2:
			return new Lesson(2);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Timetable> actual = timetableService.getAll();

	List<Timetable> expected = createTimetables();
	expected.get(0).setLessons(new ArrayList<Lesson>(Arrays.asList(new Lesson(1))));
	expected.get(1).setLessons(new ArrayList<Lesson>(Arrays.asList(new Lesson(2))));
	assertEquals(expected, actual);
    }

    @Test
    void givenTimetable_whenDelete_thenDeleted() {
	Timetable timetable = new Timetable(1);
	doNothing().when(timetableDao).deleteById(1);

	timetableService.delete(timetable);

	verify(timetableDao).deleteById(1);
    }

    private List<Timetable> createTimetables() {
	List<Timetable> timetables = new ArrayList<>();
	timetables
		.add(new Timetable(1, "main timetable", new Year(1, "first", new Speciality(1, "speciality1", null))));
	timetables.add(
		new Timetable(2, "additional timetable", new Year(1, "first", new Speciality(1, "speciality1", null))));
	return timetables;
    }

}
