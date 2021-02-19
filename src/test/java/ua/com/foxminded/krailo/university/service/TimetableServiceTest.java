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
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
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
	when(timetableDao.findById(1)).thenReturn(timetable);
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

    private Timetable createTimetable() {
	return new Timetable.TimetableBuilder().withId(1).withName("name")
		.withYear(new Year.YearBuilder().withId(1).built()).built();
    }

    private List<Timetable> createTimetables() {
	return new ArrayList<>(Arrays.asList(
		new Timetable.TimetableBuilder().withId(1).withName("name")
			.withYear(new Year.YearBuilder().withId(1).built()).built(),
		new Timetable.TimetableBuilder().withId(1).withName("name")
			.withYear(new Year.YearBuilder().withId(1).built()).built()));
    }

}
