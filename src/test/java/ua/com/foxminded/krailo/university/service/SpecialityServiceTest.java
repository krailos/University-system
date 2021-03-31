package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Specialty;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceTest {

    @Mock
    private SpecialityDao specialityDao;
    @Mock
    private YearDao yearDao;
    @InjectMocks
    private SpecialtyService specialityService;

    @Test
    void givenSpeciality_whenCereate_thenCreated() {
	Specialty speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.empty());

	specialityService.create(speciality);

	verify(specialityDao).create(speciality);
    }

    @Test
    void givenSpecialityWithNotUniqueName_whenCereate_thenNotUniqueNameExceptionThrown() {
	Specialty speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Specialty.builder().id(2).name("name").build()));

	Exception exception = assertThrows(NotUniqueNameException.class, () -> specialityService.create(speciality));

	String expectedMessage = "speciality name=name is not unique";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenSpeciality_whenUpdate_thenUpdeted() {
	Specialty speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Specialty.builder().id(1).name("name").build()));

	specialityService.update(speciality);

	verify(specialityDao).update(speciality);
    }

    @Test
    void givenSpecialityWithNotUniqueNameAndDiffrentId_whenUpdate_thenTrowServiceException() {
	Specialty speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Specialty.builder().id(2).name("name").build()));

	Exception exception = assertThrows(NotUniqueNameException.class, () -> specialityService.create(speciality));

	String expectedMessage = "speciality name=name is not unique";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void givenSpecialityId_whenGetById_thenGot() {
	Specialty speciality = createSpeciality();
	when(specialityDao.findById(1)).thenReturn(Optional.of(speciality));
	Specialty expected = createSpeciality();

	Specialty actual = specialityService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialities_whenGetAll_thenGot() {

	List<Specialty> specialities = createSpecialities();
	when(specialityDao.findAll()).thenReturn(specialities);

	List<Specialty> actual = specialityService.getAll();

	List<Specialty> expected = createSpecialities();
	assertEquals(expected, actual);
    }

    @Test
    void givenSpeciality_whenDelete_thenDeleted() {
	Specialty speciality = createSpeciality();
	doNothing().when(specialityDao).deleteById(1);

	specialityService.delete(speciality);

	verify(specialityDao).deleteById(1);
    }

    private Specialty createSpeciality() {
	return Specialty.builder().id(1).name("name").faculty(Faculty.builder().id(1).build()).build();
    }

    private List<Specialty> createSpecialities() {
	return new ArrayList<>(Arrays.asList(Specialty.builder().id(1).name("name").build(),
		Specialty.builder().id(2).name("name2").build()));
    }

}
