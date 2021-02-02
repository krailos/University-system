package ua.com.foxminded.krailo.university.service;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.BuildingDao;

@Service
public class BuildingService {

    private BuildingDao buildingDao;

    public BuildingService(BuildingDao buildingDao) {
	super();
	this.buildingDao = buildingDao;
    }
    
    
}
