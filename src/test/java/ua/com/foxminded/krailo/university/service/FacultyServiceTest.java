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
import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Speciality;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class FacultyServiceTest {

    @Mock
    private FacultyDao facultyDao;
    @Mock
    private SpecialityDao specialityDao;
    @InjectMocks
    private FacultyService facultyService;

    @Test
    void givenFaculty_whenCereate_thanCreated() {
	Faculty faculty = new Faculty("name", new DeansOffice("name", null));
	doNothing().when(facultyDao).create(faculty);

	facultyService.create(faculty);

	verify(facultyDao).create(faculty);
    }

    @Test
    void givenBuilding_whenUpdate_thanUpdeted() {
	Faculty faculty = new Faculty(1, "name", new DeansOffice("name", null));
	doNothing().when(facultyDao).update(faculty);

	facultyService.update(faculty);

	verify(facultyDao).update(faculty);
    }

    @Test
    void givenBuildingId_whenGetById_thenGot() {
	Faculty faculty = new Faculty(1, "name", new DeansOffice("name", null));
	List<Speciality> specialities = new ArrayList<>(
		Arrays.asList(new Speciality(1, "name", faculty), new Speciality(2, "name", faculty)));
	when(facultyDao.findById(1)).thenReturn(faculty);
	when(specialityDao.findByFacultyId(1)).thenReturn(specialities);
	Faculty expected = new Faculty(1, "name", new DeansOffice("name", null));
	expected.setSpecialities( new ArrayList<>(
		Arrays.asList(new Speciality(1, "name", faculty), new Speciality(2, "name", faculty))));

	Faculty actual = facultyService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenBuildings_whenGetAll_thenGot() {
	Faculty faculty = new Faculty(1, "name", new DeansOffice("name", null));
	List<Faculty> faculties = new ArrayList<>(
		Arrays.asList(new Faculty(1, "name", new DeansOffice("name", null)), new Faculty(2, "name", new DeansOffice("name", null))));
	when(facultyDao.findAll()).thenReturn(faculties);
	when(specialityDao.findByFacultyId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Speciality(1, "name", faculty);
		    case 2:
			return new Speciality(2, "name", faculty);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Faculty> actual = facultyService.getAll();

	List<Faculty> expected = new ArrayList<>(
		Arrays.asList(new Faculty(1, "name", new DeansOffice("name", null)), new Faculty(2, "name", new DeansOffice("name", null))));
	expected.get(0).setSpecialities(new ArrayList<Speciality>(Arrays.asList(new Speciality(1, "name", faculty))));
	expected.get(1).setSpecialities(new ArrayList<Speciality>(Arrays.asList(new Speciality(2, "name", faculty))));
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildingId_whenDeleteById_thenDeleted() {
	Faculty faculty = new Faculty(1, "name", new DeansOffice("name", null));
	doNothing().when(facultyDao).deleteById(1);

	facultyService.delete(faculty);

	verify(facultyDao).deleteById(1);
    }

}
