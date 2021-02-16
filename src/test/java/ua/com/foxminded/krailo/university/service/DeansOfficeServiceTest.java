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
import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class DeansOfficeServiceTest {

    @InjectMocks
    private DeansOfficeService deansOfficeService;
    @Mock
    private DeansOfficeDao deansOfficeDao;

    @Test
    void givenDeansOffice_whenCreate_thenCreated() {
	DeansOffice deansOffice = new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"));
	doNothing().when(deansOfficeDao).create(deansOffice);

	deansOfficeService.create(deansOffice);

	verify(deansOfficeDao).create(deansOffice);
    }

    @Test
    void givenDeansOffice_whenUpdate_thenUpdated() {
	DeansOffice deansOffice = new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"));
	doNothing().when(deansOfficeDao).update(deansOffice);

	deansOfficeService.update(deansOffice);

	verify(deansOfficeDao).update(deansOffice);
    }

    @Test
    void givenDeansOfficeId_whenGetById_thenGot() {
	DeansOffice deansOffice = new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"));
	when(deansOfficeDao.findById(1)).thenReturn(deansOffice);

	DeansOffice actual = deansOfficeService.getById(1);

	DeansOffice expected = new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"));
	assertEquals(expected, actual);
    }

    @Test
    void givenDeansOffices_whenGetAll_thenGot() {
	List<DeansOffice> deansOffices = new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", new UniversityOffice(1, "name", "address")),
		new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"))));
	when(deansOfficeDao.findAll()).thenReturn(deansOffices);

	List<DeansOffice> actual = deansOfficeService.getAll();
	
	List<DeansOffice> expected =new ArrayList<>(Arrays.asList(new DeansOffice(1, "name", new UniversityOffice(1, "name", "address")),
		new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"))));
	assertEquals(expected, actual);
    }

    @Test
    void givenDeansOffice_whenDelete_thenDeleted() {
	DeansOffice deansOffice = new DeansOffice(1, "name", new UniversityOffice(1, "name", "address"));
	doNothing().when(deansOfficeDao).deleteById(1);

	deansOfficeService.delete(deansOffice);

	verify(deansOfficeDao).deleteById(1);
    }

}
