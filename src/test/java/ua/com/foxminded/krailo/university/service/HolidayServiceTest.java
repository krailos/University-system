package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.StudentDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class HolidayServiceTest {

    @Mock
    private HolidayDao holidayDao;
    @InjectMocks
    private HolidayService holidayService;

    @Test
    void givenGroup_whenCereate_thanCreated() {
	Holiday holiday = new Holiday("name", LocalDate.of(2021, 01, 01));
	doNothing().when(holidayDao).create(holiday);

	holidayService.create(holiday);

	verify(holidayDao).create(holiday);
    }

    @Test
    void givenGroup_whenUpdate_thanUpdeted() {
	Holiday holiday = new Holiday("name", LocalDate.of(2021, 01, 01));
	doNothing().when(holidayDao).update(holiday);

	holidayService.update(holiday);

	verify(holidayDao).update(holiday);
    }

    @Test
    void givenGroupId_whenGetById_thenGot() {
	Holiday holiday = new Holiday(1, "name", LocalDate.of(2021, 01, 01));
	when(holidayDao.findById(1)).thenReturn(holiday);
	Holiday expected = new Holiday(1, "name", LocalDate.of(2021, 01, 01));

	Holiday actual = holidayService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenBuildings_whenGetAll_thenGot() {
	List<Holiday> holidays = new ArrayList<>(Arrays.asList(new Holiday(1, "name", LocalDate.of(2021, 01, 01)),
		new Holiday(1, "name", LocalDate.of(2021, 01, 01))));
	when(holidayDao.findAll()).thenReturn(holidays);

	List<Holiday> actual = holidayService.getAll();

	List<Holiday> expected = new ArrayList<>(Arrays.asList(new Holiday(1, "name", LocalDate.of(2021, 01, 01)),
		new Holiday(1, "name", LocalDate.of(2021, 01, 01))));
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildingId_whenDeleteById_thenDeleted() {
	Holiday holiday = new Holiday(1, "name", LocalDate.of(2021, 01, 01));
	doNothing().when(holidayDao).deleteById(1);

	holidayService.delete(holiday);

	verify(holidayDao).deleteById(1);
    }

}
