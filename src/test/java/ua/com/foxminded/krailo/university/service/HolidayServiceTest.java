package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.model.Holiday;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    @Mock
    private HolidayDao holidayDao;
    @InjectMocks
    private HolidayService holidayService;

    @Test
    void givenHoliday_whenCereate_thanCreated() {
	Holiday holiday = createHoliday();

	holidayService.create(holiday);

	verify(holidayDao).save(holiday);
    }

    @Test
    void givenHoliday_whenUpdate_thanUpdeted() {
	Holiday holiday = createHoliday();
	
	holidayService.create(holiday);

	verify(holidayDao).save(holiday);
    }

    @Test
    void givenHolidayId_whenGetById_thenGot() {
	Holiday holiday = createHoliday();
	when(holidayDao.findById(1)).thenReturn(Optional.of(holiday));
	Holiday expected = createHoliday();

	Holiday actual = holidayService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenHolidays_whenGetAll_thenGot() {
	List<Holiday> holidays = createHolidays();
	when(holidayDao.findAll()).thenReturn(holidays);

	List<Holiday> actual = holidayService.getAll();

	List<Holiday> expected = createHolidays();
	assertEquals(expected, actual);
    }

    @Test
    void givenHoliday_whenDelete_thenDeleted() {
	Holiday holiday = createHoliday();
	doNothing().when(holidayDao).delete(holiday);

	holidayService.delete(holiday);

	verify(holidayDao).delete(holiday);
    }

    private Holiday createHoliday() {
	return Holiday.builder().id(1).name("name").date(LocalDate.of(2021, 01, 01)).build();
    }

    private List<Holiday> createHolidays() {
	return new ArrayList<Holiday>(
		Arrays.asList(Holiday.builder().id(1).name("name2").date(LocalDate.of(2021, 01, 01)).build(),
			Holiday.builder().id(2).name("name2").date(LocalDate.of(2021, 02, 02)).build()));
    }
}
