package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.model.LessonTime;

@ExtendWith(SpringExtension.class)
@Transactional
@Import(ConfigTest.class)
@WebAppConfiguration
class HibernateLessonTimeDaoTest {

    @Autowired
    private LessonTimeDao lessonTimeDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewLessonTime_whenCreate_thenCreated() {
	LessonTime expected = getLessonTime();
	expected.setId(0);

	lessonTimeDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(LessonTime.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfLossonTime_whenUpdate_tnenUpdated() {
	LessonTime lessonTime = getLessonTime();
	lessonTime.setOrderNumber("new order number");

	lessonTimeDao.update(lessonTime);

	assertEquals(lessonTime, hibernateTemplate.get(LessonTime.class, lessonTime.getId()));
    }

    @Test
    void givenId_whenGetById_thenFound() {
	LessonTime expected = getLessonTime();

	LessonTime actual = lessonTimeDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenGetAll_thenFound() {
	List<LessonTime> expected = new ArrayList<>();
	expected.add(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30))
		.endTime(LocalTime.of(9, 15)).build());
	expected.add(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30))
		.endTime(LocalTime.of(10, 15)).build());
	List<LessonTime> actual = lessonTimeDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	LessonTime lessonTime = getLessonTime();

	lessonTimeDao.delete(lessonTime);

	assertNull(hibernateTemplate.get(LessonTime.class, lessonTime.getId()));
    }

    @Test
    void givenLessonTime_whenGetByStartEndLessonTime_thenFound() {
	LessonTime lessonTime = getLessonTime();

	LessonTime actual = lessonTimeDao.getByStartOrEndLessonTime(lessonTime).get();

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
