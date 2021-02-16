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
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class UniversityOfficeServiceTest {

    @Mock
    private UniversityOfficeDao universityOfficeDao;
    @Mock
    private DeansOfficeDao deansOffice;
    @Mock
    private HolidayDao holidayDao;
    @Mock
    private BuildingDao buildingDao;
    @InjectMocks
    private UniversityOfficeService universityOfficeService;

    @Test
    void givenUniversityOffice_whenCereate_thanCreated() {
	UniversityOffice universityOffice = new UniversityOffice("name","address");
	doNothing().when(universityOfficeDao).create(universityOffice);

	universityOfficeService.create(universityOffice);

	verify(universityOfficeDao).create(universityOffice);
    }

    @Test
    void givenUniversityOffice_whenUpdate_thanUpdeted() {
	UniversityOffice universityOffice = new UniversityOffice(1, "name","address");
	doNothing().when(universityOfficeDao).update(universityOffice);

	universityOfficeService.update(universityOffice);

	verify(universityOfficeDao).update(universityOffice);
    }

    @Test
    void givenUniversityOfficeId_whenGetById_thenGot() {
	UniversityOffice universityOffice = new UniversityOffice(1, "name","address");
	List<DeansOffice> deansOffices = new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", universityOffice)));
	List<Holiday> holidays = new ArrayList<>(Arrays.asList(new Holiday(1, "name", null)));
	List<Building> buildings = new ArrayList<>(Arrays.asList(new Building(1, "name", "address")));
	when(universityOfficeDao.findById(1)).thenReturn(universityOffice);
	when(deansOffice.findAll()).thenReturn(deansOffices);
	when(holidayDao.findAll()).thenReturn(holidays);
	when(buildingDao.findAll()).thenReturn(buildings);
	UniversityOffice expected = new UniversityOffice(1, "name","address");
	expected.setDeansOffices(new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", universityOffice))));
	expected.setHolidays(new ArrayList<>(Arrays.asList(new Holiday(1, "name", null))));
	expected.setBuildings(new ArrayList<>(Arrays.asList(new Building(1, "name", "address"))));

	UniversityOffice actual = universityOfficeService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenUniversityOffices_whenGetAll_thenGot() {
	UniversityOffice universityOffice = new UniversityOffice(1, "name","address");
	List<UniversityOffice> universityOffices = new ArrayList<>(Arrays.asList(new UniversityOffice(1, "name","address"),
	new UniversityOffice(2, "name","address")));
	List<DeansOffice> deansOffices = new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", universityOffice)));
	List<Holiday> holidays = new ArrayList<>(Arrays.asList(new Holiday(1, "name", null)));
	List<Building> buildings = new ArrayList<>(Arrays.asList(new Building(1, "name", "address")));
	when(universityOfficeDao.findAll()).thenReturn(universityOffices);
	when(deansOffice.findAll()).thenReturn(deansOffices);
	when(holidayDao.findAll()).thenReturn(holidays);
	when(buildingDao.findAll()).thenReturn(buildings);

	List<UniversityOffice> actual = universityOfficeService.getAll();

	List<UniversityOffice> expected =  new ArrayList<>(Arrays.asList(new UniversityOffice(1, "name","address"),
		new UniversityOffice(2, "name","address")));
	expected.get(0).setDeansOffices(new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", universityOffice))));
	expected.get(0).setHolidays(new ArrayList<>(Arrays.asList(new Holiday(1, "name", null))));
	expected.get(0).setBuildings(new ArrayList<>(Arrays.asList(new Building(1, "name", "address"))));
	expected.get(1).setDeansOffices(new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", universityOffice))));
	expected.get(1).setHolidays(new ArrayList<>(Arrays.asList(new Holiday(1, "name", null))));
	expected.get(1).setBuildings(new ArrayList<>(Arrays.asList(new Building(1, "name", "address"))));
	
	assertEquals(expected, actual);
    }

    @Test
    void givenUniversityOfficeId_whenDeleteById_thenDeleted() {
	UniversityOffice universityOffice = new UniversityOffice(1, "name","address");
	doNothing().when(universityOfficeDao).deleteById(1);

	universityOfficeService.delete(universityOffice);

	verify(universityOfficeDao).deleteById(1);
    }

}
