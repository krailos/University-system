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
import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;

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
	Faculty faculty = createFaculty();
	doNothing().when(facultyDao).create(faculty);

	facultyService.create(faculty);

	verify(facultyDao).create(faculty);
    }

    @Test
    void givenFaculty_whenUpdate_thanUpdeted() {
	Faculty faculty = createFaculty();
	doNothing().when(facultyDao).update(faculty);

	facultyService.update(faculty);

	verify(facultyDao).update(faculty);
    }

    @Test
    void givenFacultyId_whenGetById_thenGot() {
	Faculty faculty = createFaculty();
	when(facultyDao.findById(1)).thenReturn(faculty);

	Faculty actual = facultyService.getById(1);

	Faculty expected = createFaculty();
	assertEquals(expected, actual);
    }

    @Test
    void givenFaculties_whenGetAll_thenGot() {
	List<Faculty> faculties = createFaculties();
	when(facultyDao.findAll()).thenReturn(faculties);

	List<Faculty> actual = facultyService.getAll();

	List<Faculty> expected = createFaculties();
	assertEquals(expected, actual);
    }

    @Test
    void givenFaculty_whenDelete_thenDeleted() {
	Faculty faculty = createFaculty();
	doNothing().when(facultyDao).deleteById(1);

	facultyService.delete(faculty);

	verify(facultyDao).deleteById(1);
    }

    private Faculty createFaculty() {
	return new Faculty.FacultyBuilder().withId(1).withName("name")
		.withDeansOffice(new DeansOffice.DeansOfficeBuilder().withId(1).built()).built();
    }

    private List<Faculty> createFaculties() {
	return new ArrayList<Faculty>(Arrays.asList(
		new Faculty.FacultyBuilder().withId(1).withName("name1")
			.withDeansOffice(new DeansOffice.DeansOfficeBuilder().withId(1).built()).built(),
		new Faculty.FacultyBuilder().withId(2).withName("name2")
			.withDeansOffice(new DeansOffice.DeansOfficeBuilder().withId(1).built()).built()));
    }

}
