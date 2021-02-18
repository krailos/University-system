package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.model.Building;

@Service
public class BuildingService {

    private BuildingDao buildingDao;

    public BuildingService(BuildingDao buildingDao) {
	this.buildingDao = buildingDao;
    }

    public void create(Building building) {
	buildingDao.create(building);
    }

    public void update(Building building) {
	buildingDao.update(building);
    }

    public Building getById(int id) {
	return buildingDao.findById(id);
	
    }

    public List<Building> getAll() {
	return buildingDao.findAll();

    }

    public void delete(Building building) {
	buildingDao.deleteById(building.getId());
    }


}
