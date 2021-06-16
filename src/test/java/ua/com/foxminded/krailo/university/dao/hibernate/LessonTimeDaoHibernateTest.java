package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.LessonTimeDaoInt;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonTimeDaoHibernateTest {

    @Autowired
    private LessonTimeDaoInt lessonTimeDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewLessonTime_whenCreate_thenCreated() {
	LessonTime lessonTime = getLessonTime();
	lessonTime.setId(0);

	lessonTimeDao.create(lessonTime);

	assertEquals(lessonTime.getId(), hibernateTemplate.get(LessonTime.class, 3).getId());
    }

    @Test
    void givenNewFieldsOfLossonTime_whenUpdate_tnenUpdated() {
	LessonTime lessonTime = getLessonTime();
	lessonTime.setOrderNumber("new order number");

	lessonTimeDao.update(lessonTime);

	assertEquals(lessonTime, hibernateTemplate.get(LessonTime.class, 1));
    }

    @Test
    void givenId_whenGetById_thenFound() {
	LessonTime expected = getLessonTime();

	LessonTime actual = lessonTimeDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonTimes_whenGetAll_thenFound() {

	int actual = lessonTimeDao.getAll().size();

	assertEquals(2, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	LessonTime lessonTime = getLessonTime();
	
	lessonTimeDao.delete(lessonTime);

	assertEquals(null, hibernateTemplate.get(LessonTime.class,1));
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
