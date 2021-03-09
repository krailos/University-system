package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@ExtendWith(MockitoExtension.class)
class LessonTimeSceduleServiceTest {

    @Mock
    private LessonTimeScheduleDao lessonTimeScheduleDao;
    @Mock
    private LessonTimeDao lessonTimeDao;
    @InjectMocks
    private LessonsTimeScheduleService lessonsTimeScheduleService;

    @Test
    void givenLessonsTimeSchedule_whenCereate_thanCreated() {
	LessonsTimeSchedule lessonsTimeSchedule = createLessonsTimeSchedule();
	doNothing().when(lessonTimeScheduleDao).create(lessonsTimeSchedule);

	lessonsTimeScheduleService.create(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).create(lessonsTimeSchedule);
    }

    @Test
    void givenLessonsTimeSchedule_whenUpdate_thanUpdeted() {
	LessonsTimeSchedule lessonsTimeSchedule = createLessonsTimeSchedule();
	doNothing().when(lessonTimeScheduleDao).update(lessonsTimeSchedule);

	lessonsTimeScheduleService.update(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).update(lessonsTimeSchedule);
    }

    @Test
    void givenLessonsTimeScheduleId_whenGetById_thenGot() {
	LessonsTimeSchedule lessonsTimeSchedule = createLessonsTimeSchedule();
	when(lessonTimeScheduleDao.findById(1)).thenReturn(Optional.of(lessonsTimeSchedule));
	LessonsTimeSchedule expected = createLessonsTimeSchedule();

	LessonsTimeSchedule actual = lessonsTimeScheduleService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsTimeSchedules_whenGetAll_thenGot() {
	List<LessonsTimeSchedule> lessonsTimeSchedules = createLessonsTimeSchedules();
	when(lessonTimeScheduleDao.findAll()).thenReturn(lessonsTimeSchedules);

	List<LessonsTimeSchedule> actual = lessonsTimeScheduleService.getAll();

	List<LessonsTimeSchedule> expected = createLessonsTimeSchedules();
	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsTimeSchedule_whenDelete_thenDeleted() {
	LessonsTimeSchedule lessonsTimeSchedule = createLessonsTimeSchedule();
	doNothing().when(lessonTimeScheduleDao).deleteById(1);

	lessonsTimeScheduleService.delete(lessonsTimeSchedule);

	verify(lessonTimeScheduleDao).deleteById(1);
    }

    private LessonsTimeSchedule createLessonsTimeSchedule() {
	return LessonsTimeSchedule.builder().id(1).name("name").build();
    }

    private List<LessonsTimeSchedule> createLessonsTimeSchedules() {
	return new ArrayList<>(Arrays.asList(LessonsTimeSchedule.builder().id(1).name("name").build()));
    }
}
