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
import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class YearServiceTest {

    @Mock
    private YearDao yearDao;
    @Mock
    private GroupDao groupDao;
    @InjectMocks
    private YearService yearService;

    @Test
    void givenYear_whenCereate_thanCreated() {
	Year year = new Year(1, "name", null);
	doNothing().when(yearDao).create(year);

	yearService.create(year);

	verify(yearDao).create(year);
    }

    @Test
    void givenYear_whenUpdate_thanUpdeted() {
	Year year = new Year(1, "name", null);
	doNothing().when(yearDao).update(year);

	yearService.update(year);

	verify(yearDao).update(year);
    }

    @Test
    void givenYearId_whenGetById_thenGot() {
	Year year = new Year(1, "name", null);
	List<Group> groups = new ArrayList<>(Arrays.asList(new Group(1, "name", year), new Group(2, "name", year)));
	when(yearDao.findById(1)).thenReturn(year);
	when(groupDao.findByYearId(1)).thenReturn(groups);
	Year expected = new Year(1, "name", null);
	expected.setGroups(new ArrayList<>(Arrays.asList(new Group(1, "name", year), new Group(2, "name", year))));

	Year actual = yearService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenYears_whenGetAll_thenGot() {
	Year year = new Year(1, "name", null);
	List<Year> years = new ArrayList<>(Arrays.asList(new Year(1, "name", null), new Year(2, "name", null)));
	when(yearDao.findAll()).thenReturn(years);
	when(groupDao.findByYearId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Group(1, "name", year);
		    case 2:
			return new Group(2, "name", year);
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Year> actual = yearService.getAll();

	List<Year> expected = new ArrayList<>(Arrays.asList(new Year(1, "name", null), new Year(2, "name", null)));
	expected.get(0).setGroups(new ArrayList<Group>(Arrays.asList(new Group(1, "name", year))));
	expected.get(1).setGroups(new ArrayList<Group>(Arrays.asList(new Group(2, "name", year))));
	assertEquals(expected, actual);
    }

    @Test
    void givenYearId_whenDeleteById_thenDeleted() {
	Year year = new Year(1, "name", null);
	doNothing().when(yearDao).deleteById(1);

	yearService.delete(year);

	verify(yearDao).deleteById(1);
    }

}
