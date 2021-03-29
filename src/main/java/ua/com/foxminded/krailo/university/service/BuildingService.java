package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Building;

@Service
public class BuildingService {

    private static final Logger log = LoggerFactory.getLogger(BuildingService.class);

    private BuildingDao buildingDao;

    public BuildingService(BuildingDao buildingDao) {
	this.buildingDao = buildingDao;
    }

    public void create(Building building) {
	log.debug("create building={}", building);
	buildingDao.create(building);
    }

    public void update(Building building) {
	log.debug("update building={}", building);
	buildingDao.update(building);
    }

    public Building getById(int id) {
	log.debug("get building by id={}", id);
	return buildingDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Building whith id=%s not exist", id)));
    }

    public List<Building> getAll() {
	log.debug("get all buildings");
	return buildingDao.findAll();
    }

    public void delete(Building building) {
	log.debug("delete building={}", building);
	buildingDao.deleteById(building.getId());
    }

}
