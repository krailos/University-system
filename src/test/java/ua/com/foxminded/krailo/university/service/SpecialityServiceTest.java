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
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Faculty;
import ua.com.foxminded.krailo.university.model.Speciality;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceTest {

    @Mock
    private SpecialityDao specialityDao;
    @Mock
    private YearDao yearDao;
    @InjectMocks
    private SpecialityService specialityService;

    @Test
    void givenSpeciality_whenCereate_thanCreated() {
	Speciality speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.empty());

	specialityService.create(speciality);

	verify(specialityDao).create(speciality);
    }

    @Test
    void givenSpecialityWithNotUniqueName_whenCereate_thanTrowServiceException() {
	Speciality speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Speciality.builder().id(2).name("name").build()));

	assertThrows(ServiceException.class, () -> specialityService.create(speciality));
    }

    @Test
    void givenSpeciality_whenUpdate_thanUpdeted() {
	Speciality speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Speciality.builder().id(1).name("name").build()));

	specialityService.update(speciality);

	verify(specialityDao).update(speciality);
    }

    @Test
    void givenSpecialityWithNotUniqueNam_whenUpdate_thanTrowServiceException() {
	Speciality speciality = createSpeciality();
	when(specialityDao.findByNameAndFacultyId(speciality.getName(), speciality.getFaculty().getId()))
		.thenReturn(Optional.of(Speciality.builder().id(2).name("name").build()));

	assertThrows(ServiceException.class, () -> specialityService.update(speciality));

    }

    @Test
    void givenSpecialityId_whenGetById_thenGot() {
	Speciality speciality = createSpeciality();
	when(specialityDao.findById(1)).thenReturn(Optional.of(speciality));
	Speciality expected = createSpeciality();

	Speciality actual = specialityService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialities_whenGetAll_thenGot() {

	List<Speciality> specialities = createSpecialities();
	when(specialityDao.findAll()).thenReturn(specialities);

	List<Speciality> actual = specialityService.getAll();

	List<Speciality> expected = createSpecialities();
	assertEquals(expected, actual);
    }

    @Test
    void givenSpeciality_whenDelete_thenDeleted() {
	Speciality speciality = createSpeciality();
	doNothing().when(specialityDao).deleteById(1);

	specialityService.delete(speciality);

	verify(specialityDao).deleteById(1);
    }

    private Speciality createSpeciality() {
	return Speciality.builder().id(1).name("name").faculty(Faculty.builder().id(1).build()).build();
    }

    private List<Speciality> createSpecialities() {
	return new ArrayList<>(Arrays.asList(Speciality.builder().id(1).name("name").build(),
		Speciality.builder().id(2).name("name2").build()));
    }

}
