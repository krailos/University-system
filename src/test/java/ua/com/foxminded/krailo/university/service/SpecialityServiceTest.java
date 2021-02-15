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
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class SpecialityServiceTest {

    @Mock
    private SpecialityDao specialityDao;
    @Mock
    private YearDao yearDao;
    @InjectMocks
    private SpecialityService specialityService;

    @Test
    void givenSpeciality_whenCereate_thanCreated() {
	Speciality speciality = new Speciality("name", new Faculty("name", null));
	doNothing().when(specialityDao).create(speciality);

	specialityService.create(speciality);

	verify(specialityDao).create(speciality);
    }

    @Test
    void givenSpeciality_whenUpdate_thanUpdeted() {
	Speciality speciality = new Speciality(1, "name", new Faculty("name", null));
	doNothing().when(specialityDao).update(speciality);

	specialityService.update(speciality);

	verify(specialityDao).update(speciality);
    }

    @Test
    void givenSpecialityId_whenGetById_thenGot() {
	Speciality speciality = new Speciality(1, "name", new Faculty("name", null));
	List<Year> years = new ArrayList<>(
		Arrays.asList(new Year(1, "name", speciality), new Year(2, "name", speciality)));
	when(specialityDao.findById(1)).thenReturn(speciality);
	when(yearDao.findBySpecialityId(1)).thenReturn(years);
	Speciality expected = new Speciality("name", new Faculty("name", null));
	expected.setYears(
		new ArrayList<>(Arrays.asList(new Year(1, "name", speciality), new Year(2, "name", speciality))));

	Speciality actual = specialityService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialities_whenGetAll_thenGot() {
	Faculty faculty = new Faculty(1, "name", new DeansOffice("name", null));
	List<Speciality> specialities= new ArrayList<>(
		Arrays.asList(new Speciality(1, "name", faculty), new Speciality(1, "name", faculty)));
	when(specialityDao.findAll()).thenReturn(specialities);
	when(yearDao.findBySpecialityId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Year(1, "name", new Speciality());
		    case 2:
			return new Year(2, "name", new Speciality());
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Speciality> actual = specialityService.getAll();

	List<Speciality> expected =  new ArrayList<>(
		Arrays.asList(new Speciality(1, "name", faculty), new Speciality(1, "name", faculty)));
	expected.get(0).setYears(new ArrayList<Year>(Arrays.asList(new Year(1, "name", new Speciality()))));
	expected.get(1).setYears(new ArrayList<Year>(Arrays.asList(new Year(1, "name", new Speciality()))));
	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialityId_whenDeleteById_thenDeleted() {
	Speciality speciality = new Speciality(1, "name", new Faculty("name", null));
	doNothing().when(specialityDao).deleteById(1);

	specialityService.delete(speciality);

	verify(specialityDao).deleteById(1);
    }

}
