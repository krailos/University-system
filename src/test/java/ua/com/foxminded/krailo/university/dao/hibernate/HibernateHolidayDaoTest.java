package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.model.Holiday;

@Transactional
@SpringBootTest
@ContextConfiguration(classes = ConfigTest.class)
class HibernateHolidayDaoTest {

    @Autowired
    private HibernateHolidayDao holidayDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenHolidayId_whenGetById_thenGot() {
	Holiday expected = Holiday.builder().id(1).name("Holiday 1").date(LocalDate.of(2021, 01, 01)).build();

	Holiday actual = holidayDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenDate_whenGetHolidayByDate_thenHolidayReturned() {
	Holiday expected = Holiday.builder().id(1).name("Holiday 1").date(LocalDate.of(2021, 01, 01)).build();

	Holiday actual = holidayDao.getByDate(LocalDate.of(2021, 01, 01)).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenHoliday_whenUpdate_thenUpdated() {
	Holiday holiday = Holiday.builder().id(1).name("new Holiday").date(LocalDate.of(2021, 01, 01)).build();

	holidayDao.update(holiday);

	assertEquals(holiday, hibernateTemplate.get(Holiday.class, holiday.getId()));
    }

    @Test
    void givenNewHoliday_whenCreate_thenCreated() {
	Holiday expected = Holiday.builder().id(0).name("new Holiday").date(LocalDate.of(2021, 02, 02)).build();

	holidayDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Holiday.class, expected.getId()));
    }

    @Test
    void givenHoliday_whenDelete_thenDeleted() {
	Holiday holiday = Holiday.builder().id(2).name("new Holiday").date(LocalDate.of(2021, 02, 02)).build();

	holidayDao.delete(holiday);

	assertNull(hibernateTemplate.get(Holiday.class, holiday.getId()));
    }

    @Test
    void whenGetAllHolidays_thenAllHolidaysReturned() {
	List<Holiday> expected = new ArrayList<>();
	expected.add(Holiday.builder().id(1).name("Holiday 1").date(LocalDate.of(2021, 01, 01)).build());
	expected.add(Holiday.builder().id(2).name("Holiday 2").date(LocalDate.of(2021, 01, 02)).build());

	List<Holiday> actual = holidayDao.getAll();

	assertEquals(expected, actual);
    }

}
