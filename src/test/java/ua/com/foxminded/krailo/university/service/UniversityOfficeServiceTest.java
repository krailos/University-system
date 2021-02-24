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

import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@ExtendWith(MockitoExtension.class)
class UniversityOfficeServiceTest {

    @Mock
    private UniversityOfficeDao universityOfficeDao;
    @InjectMocks
    private UniversityOfficeService universityOfficeService;

    @Test
    void givenUniversityOffice_whenCereate_thanCreated() {
	UniversityOffice universityOffice = createUniversityOffice();
	doNothing().when(universityOfficeDao).create(universityOffice);

	universityOfficeService.create(universityOffice);

	verify(universityOfficeDao).create(universityOffice);
    }

    @Test
    void givenUniversityOffice_whenUpdate_thanUpdeted() {
	UniversityOffice universityOffice = createUniversityOffice();
	doNothing().when(universityOfficeDao).update(universityOffice);

	universityOfficeService.update(universityOffice);

	verify(universityOfficeDao).update(universityOffice);
    }

    @Test
    void givenUniversityOfficeId_whenGetById_thenGot() {
	UniversityOffice universityOffice = createUniversityOffice();
	when(universityOfficeDao.findById(1)).thenReturn(universityOffice);
	UniversityOffice expected = createUniversityOffice();

	UniversityOffice actual = universityOfficeService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenUniversityOffices_whenGetAll_thenGot() {

	List<UniversityOffice> universityOffices = createUniversityOffices();
	when(universityOfficeDao.findAll()).thenReturn(universityOffices);

	List<UniversityOffice> actual = universityOfficeService.getAll();

	List<UniversityOffice> expected = createUniversityOffices();
	assertEquals(expected, actual);
    }

    @Test
    void givenUniversityOffice_whenDelete_thenDeleted() {
	UniversityOffice universityOffice = createUniversityOffice();
	doNothing().when(universityOfficeDao).deleteById(1);

	universityOfficeService.delete(universityOffice);

	verify(universityOfficeDao).deleteById(1);
    }

    private UniversityOffice createUniversityOffice() {
	return UniversityOffice.builder().id(1).name("name").address("address").build();
    }

    private List<UniversityOffice> createUniversityOffices() {
	return new ArrayList<>(Arrays.asList(UniversityOffice.builder().id(1).name("name").address("address").build(),
		UniversityOffice.builder().id(1).name("name").address("address").build()));
    }

}
