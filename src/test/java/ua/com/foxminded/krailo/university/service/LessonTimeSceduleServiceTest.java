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
import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class LessonTimeSceduleServiceTest {

    @Mock
    private LessonTimeScheduleDao lessonTimeScheduleDao;
    @Mock
    private LessonTimeDao lessonTimeDao;
    @InjectMocks
    private LessonsTimeScheduleService lessonsTimeScheduleService;

    @Test
    void givenLessonsTimeSchedule_whenCereate_thanCreated() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "name");
	doNothing().when(lessonTimeScheduleDao).create(lessonsTimeSchedule);

	lessonsTimeScheduleService.create(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).create(lessonsTimeSchedule);
    }

    @Test
    void givenLessonsTimeSchedule_whenUpdate_thanUpdeted() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "name");
	doNothing().when(lessonTimeScheduleDao).update(lessonsTimeSchedule);

	lessonsTimeScheduleService.update(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).update(lessonsTimeSchedule);
    }

    @Test
    void givenLessonsTimeScheduleId_whenGetById_thenGot() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "name");
	List<LessonTime> lessonTimes = new ArrayList<>(
		Arrays.asList(new LessonTime(1), new LessonTime(2)));
	when(lessonTimeScheduleDao.findById(1)).thenReturn(lessonsTimeSchedule);
	when(lessonTimeDao.findBylessonTimeScheduleId(1)).thenReturn(lessonTimes);
	LessonsTimeSchedule expected = new LessonsTimeSchedule(1, "name");
	expected.setLessonTimes(new ArrayList<>(
		Arrays.asList(new LessonTime(1), new LessonTime(2))));

		LessonsTimeSchedule actual = lessonsTimeScheduleService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsTimeSchedules_whenGetAll_thenGot() {
	List<LessonsTimeSchedule> lessonsTimeSchedules = new ArrayList<>(
		Arrays.asList(new LessonsTimeSchedule(1, "name"), new LessonsTimeSchedule(2, "name")));
	when(lessonTimeScheduleDao.findAll()).thenReturn(lessonsTimeSchedules);
	when(lessonTimeDao.findBylessonTimeScheduleId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new LessonTime(1);
		    case 2:
			return new LessonTime(2);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<LessonsTimeSchedule> actual = lessonsTimeScheduleService.getAll();

	List<LessonsTimeSchedule> expected = new ArrayList<>(
		Arrays.asList(new LessonsTimeSchedule(1, "name"), new LessonsTimeSchedule(2, "name")));
	expected.get(0).setLessonTimes(new ArrayList<LessonTime>(Arrays.asList(new LessonTime(1))));
	expected.get(1).setLessonTimes(new ArrayList<LessonTime>(Arrays.asList(new LessonTime(2))));
	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsTimeSchedule_whenDelete_thenDeleted() {
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "name");
	doNothing().when(lessonTimeScheduleDao).deleteById(1);

	lessonsTimeScheduleService.delete(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).deleteById(1);
    }

}
