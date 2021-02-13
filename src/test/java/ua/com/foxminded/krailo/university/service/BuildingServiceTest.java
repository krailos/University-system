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
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class BuildingServiceTest {

    @Mock
    private BuildingDao buildingDao;
    @Mock
    private AudienceDao audienceDao;
    @InjectMocks
    private BuildingService buildingService;

    @Test
    void givenBuilding_whenCereate_thanCreated() {
	Building building = new Building(1, "name", "address");
	doNothing().when(buildingDao).create(building);

	buildingService.create(building);

	verify(buildingDao).create(building);
    }

    @Test
    void givenBuilding_whenUpdate_thanUpdeted() {
	Building building = new Building(1, "name", "address");
	doNothing().when(buildingDao).update(building);

	buildingService.update(building);

	verify(buildingDao).update(building);
    }

    @Test
    void givenBuildingId_whenGetById_thenGot() {
	Building building = new Building(1, "name", "address");
	List<Audience> audiences = new ArrayList<>(
		Arrays.asList(new Audience(1, "new", null, 100, ""), new Audience(1, "new", null, 100, "")));
	when(buildingDao.findById(1)).thenReturn(building);
	when(audienceDao.findByBuildingId(1)).thenReturn(audiences);
	Building expected = new Building(1, "name", "address");
	expected.setAudiences(new ArrayList<>(
		Arrays.asList(new Audience(1, "new", null, 100, ""), new Audience(1, "new", null, 100, ""))));

	Building actual = buildingService.getById(1);

	assertEquals(expected, actual);
    }

    @Test
    void givenBuildings_whenGetAll_thenGot() {
	List<Building> buildings = new ArrayList<>(
		Arrays.asList(new Building(1, "name", "address"), new Building(2, "name2", "address2")));
	when(buildingDao.findAll()).thenReturn(buildings);
	when(audienceDao.findByBuildingId(any(Integer.class)))
		.thenAnswer(inv -> Arrays.stream(inv.getArguments()).map(o -> (int) o).map(i -> {
		    switch (i) {
		    case 1:
			return new Audience(1, "new", null, 100, "");
		    case 2:
			return new Audience(2, "new2", null, 100, "");
		    default:
			return null;
		    }
		}).collect(Collectors.toList()));

	List<Building> actual = buildingService.getAll();

	List<Building> expected = new ArrayList<>(
		Arrays.asList(new Building(1, "name", "address"), new Building(2, "name2", "address2")));
	expected.get(0).setAudiences(new ArrayList<Audience>(Arrays.asList(new Audience(1, "new", null, 100, ""))));
	expected.get(1).setAudiences(new ArrayList<Audience>(Arrays.asList(new Audience(2, "new2", null, 100, ""))));
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildingId_whenDeleteById_thenDeleted() {
	Building building = new Building(1, "name", "address");
	doNothing().when(buildingDao).deleteById(1);

	buildingService.delete(building);

	verify(buildingDao).deleteById(1);
    }

}
