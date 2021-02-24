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

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.Faculty;

@ExtendWith(MockitoExtension.class)
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
	return Faculty.builder().id(1).name("name")
		.deansOffice(DeansOffice.builder().id(1).build()).build();
    }

    private List<Faculty> createFaculties() {
	return new ArrayList<Faculty>(Arrays.asList(
		Faculty.builder().id(1).name("name1")
			.deansOffice(DeansOffice.builder().id(1).build()).build(),
		Faculty.builder().id(2).name("name2")
			.deansOffice(DeansOffice.builder().id(1).build()).build()));
    }

}
