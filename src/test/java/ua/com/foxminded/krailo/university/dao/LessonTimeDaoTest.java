package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Transactional
@DataJpaTest
@ContextConfiguration(classes = ConfigTest.class)
class LessonTimeDaoTest {

    @Autowired
    private LessonTimeDao lessonTimeDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenNewLessonTime_whenCreate_thenCreated() {
	LessonTime expected = getLessonTime();
	expected.setId(0);

	lessonTimeDao.save(expected);

	assertEquals(expected, entityManager.find(LessonTime.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfLossonTime_whenUpdate_tnenUpdated() {
	LessonTime lessonTime = getLessonTime();
	lessonTime.setOrderNumber("new order number");

	lessonTimeDao.save(lessonTime);

	assertEquals(lessonTime, entityManager.find(LessonTime.class, lessonTime.getId()));
    }

    @Test
    void givenId_whenGetById_thenFound() {
	LessonTime expected = getLessonTime();

	LessonTime actual = lessonTimeDao.findById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenGetAll_thenFound() {
	List<LessonTime> expected = new ArrayList<>();
	expected.add(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30))
		.endTime(LocalTime.of(9, 15)).build());
	expected.add(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30))
		.endTime(LocalTime.of(10, 15)).build());
	List<LessonTime> actual = lessonTimeDao.findAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	LessonTime lessonTime = getLessonTime();

	lessonTimeDao.delete(lessonTime);

	assertNull(entityManager.find(LessonTime.class, lessonTime.getId()));
    }

    @Test
    void givenLessonTime_whenGetByStartEndLessonTime_thenFound() {
	LessonTime lessonTime = getLessonTime();

	LessonTime actual = lessonTimeDao
		.findByStartTimeAndEndTimeBetween(lessonTime.getStartTime(), lessonTime.getEndTime()).get();

	assertTrue(actual.getStartTime().isAfter(LocalTime.of(8, 29))
		&& actual.getStartTime().isBefore(LocalTime.of(9, 16)));
	assertTrue(
		actual.getEndTime().isAfter(LocalTime.of(8, 29)) && actual.getEndTime().isBefore(LocalTime.of(9, 16)));
    }

    private LessonTime getLessonTime() {
	return LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30))
		.endTime(LocalTime.of(9, 15)).build();
    }

}
