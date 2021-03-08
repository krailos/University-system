package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.model.Building;

@ExtendWith(MockitoExtension.class)
class BuildingServiceTest {

    @Mock
    private BuildingDao buildingDao;
    @Mock
    private AudienceDao audienceDao;
    @InjectMocks
    private BuildingService buildingService;

    @Test
    void givenBuilding_whenCereate_thanCreated() {
	Building building = createBuilding();
	doNothing().when(buildingDao).create(building);

	buildingService.create(building);

	verify(buildingDao).create(building);
    }

    @Test
    void givenBuilding_whenUpdate_thanUpdeted() {
	Building building = createBuilding();
	doNothing().when(buildingDao).update(building);

	buildingService.update(building);

	verify(buildingDao).update(building);
    }

    @Test
    void givenBuildingId_whenGetById_thenGot() {
	Building building = createBuilding();
	when(buildingDao.findById(1)).thenReturn(Optional.of(building));

	Building actual = buildingService.getById(1);

	Building expected = createBuilding();
	assertEquals(expected, actual);
    }

    @Test
    void givenBuildings_whenGetAll_thenGot() {
	List<Building> buildings = createBuildings();
	when(buildingDao.findAll()).thenReturn(buildings);

	List<Building> actual = buildingService.getAll();

	List<Building> expected = createBuildings();
	assertEquals(expected, actual);
    }

    @Test
    void givenBuilding_whenDelete_thenDeleted() {
	Building building = createBuilding();
	doNothing().when(buildingDao).deleteById(1);

	buildingService.delete(building);

	verify(buildingDao).deleteById(1);
    }

    private Building createBuilding() {
	return Building.builder().id(1).name("name").address("address").build();
    }

    private List<Building> createBuildings() {
	List<Building> buildings = new ArrayList<>();
	Building building1 = Building.builder().id(1).name("name1").address("address1").build();
	Building building2 = Building.builder().id(2).name("name2").address("address2").build();
	buildings.add(building1);
	buildings.add(building2);
	return buildings;
    }

}
