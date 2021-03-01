package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.time.LocalTime;
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
import ua.com.foxminded.krailo.university.model.LessonTime;

@ExtendWith(MockitoExtension.class)
class LessonTimeServiceTest {

    @Mock
    private LessonTimeDao lessonTimeDao;
    @InjectMocks
    private LessonTimeService lessonTimeService;

    @Test
    void givenLessonTime_whenCereate_thanCreated() {
	LessonTime lessonTime = createLessonTime();

	lessonTimeService.create(lessonTime);

	verify(lessonTimeDao).create(lessonTime);
    }

    @Test
    void givenExistingLessonTime_whenCereate_thanNotCreated() {
	LessonTime lessonTime = createLessonTime();
	when(lessonTimeDao.findByStartOrEndLessonTime(lessonTime))
		.thenReturn(Optional.of(LessonTime.builder().id(1).build()));

	lessonTimeService.create(lessonTime);

	verify(lessonTimeDao, never()).create(lessonTime);
    }

    @Test
    void givenGLessonTime_whenUpdate_thanUpdeted() {
	LessonTime lessonTime = createLessonTime();
	doNothing().when(lessonTimeDao).update(lessonTime);

	lessonTimeService.update(lessonTime);

	verify(lessonTimeDao).update(lessonTime);
    }

    @Test
    void givenExistingLessonTime_whenUpdate_thanNotUpdated() {
	LessonTime lessonTime = createLessonTime();
	when(lessonTimeDao.findByStartOrEndLessonTime(lessonTime))
		.thenReturn(Optional.of(LessonTime.builder().id(1).build()));

	lessonTimeService.update(lessonTime);

	verify(lessonTimeDao, never()).update(lessonTime);
    }

    @Test
    void givenLessonTimeId_whenGetById_thenGot() {
	LessonTime lessonTime = createLessonTime();
	when(lessonTimeDao.findById(1)).thenReturn(lessonTime);
	LessonTime expected = createLessonTime();

	LessonTime actual = lessonTimeService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenGetAll_thenGot() {
	List<LessonTime> lessonTimes = createLessonTimes();
	when(lessonTimeDao.findAll()).thenReturn(lessonTimes);

	List<LessonTime> actual = lessonTimeService.getAll();

	List<LessonTime> expected = createLessonTimes();
	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTime_whenDelete_thenDeleted() {
	LessonTime lessonTime = createLessonTime();
	doNothing().when(lessonTimeDao).deleteById(1);

	lessonTimeService.delete(lessonTime);

	verify(lessonTimeDao).deleteById(1);
    }

    private LessonTime createLessonTime() {
	return LessonTime.builder().id(1).orderNumber("order numver").startTime(LocalTime.of(8, 30))
		.endTime(LocalTime.of(9, 15)).build();
    }

    private List<LessonTime> createLessonTimes() {
	return new ArrayList<>(Arrays.asList(LessonTime.builder().id(1).orderNumber("order numver")
		.startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build()));
    }

}
