package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ua.com.foxminded.krailo.university.dao.interf.YearDao;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
class YearServiceTest {

    @Mock
    private YearDao yearDao;
    @InjectMocks
    private YearService yearService;

    @Test
    void givenYear_whenCereate_thanCreated() {
	Year year = createYear();
	doNothing().when(yearDao).create(year);

	yearService.create(year);

	verify(yearDao).create(year);
    }

    @Test
    void givenYear_whenUpdate_thanUpdeted() {
	Year year = createYear();
	doNothing().when(yearDao).update(year);

	yearService.update(year);

	verify(yearDao).update(year);
    }

    @Test
    void givenYearId_whenGetById_thenGot() {
	Year year = createYear();
	when(yearDao.getById(1)).thenReturn(Optional.of(year));
	Year expected = createYear();

	Year actual = yearService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenYears_whenGetAll_thenGot() {
	List<Year> years = createYears();
	when(yearDao.getAll()).thenReturn(years);

	List<Year> actual = yearService.getAll();

	List<Year> expected = createYears();

	assertEquals(expected, actual);
    }

    @Test
    void givenYear_whenDelete_thenDeleted() {
	Year year = createYear();
	doNothing().when(yearDao).delete(year);

	yearService.delete(year);

	verify(yearDao).delete(year);
    }

    private Year createYear() {
	return Year.builder().id(1).name("year").build();
    }

    private List<Year> createYears() {
	return new ArrayList<>(
		Arrays.asList(Year.builder().id(1).name("year").build(), Year.builder().id(2).name("year2").build()));
    }

}
