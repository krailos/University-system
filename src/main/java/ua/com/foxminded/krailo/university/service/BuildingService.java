package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.model.Timetable;

@Service
public class BuildingService {

    private BuildingDao buildingDao;
    private AudienceDao audienceDao;

    public BuildingService(BuildingDao buildingDao, AudienceDao audienceDao) {
	this.buildingDao = buildingDao;
	this.audienceDao = audienceDao;
    }

    public void create(Building building) {
	buildingDao.create(building);
    }

    public void update(Building building) {
	buildingDao.update(building);
    }

    public Building getById(int id) {
	Building building = buildingDao.findById(id);
	addAudiencesToBuilding(building);
	return building;
    }

    public List<Building> getAll() {
	List<Building> buildings = buildingDao.findAll();
	for (Building building : buildings) {
	    addAudiencesToBuilding(building);
	}
	return buildings;
    }

    public void delete(Building building) {
	buildingDao.deleteById(building.getId());
    }

    private void addAudiencesToBuilding(Building building) {
	building.setAudiences(audienceDao.findByBuildingId(building.getId()));
    }

}
