package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private AudienceRowMapper audienceRowMapper;
    
    @Autowired
    private BuildingDao buildingDao;
    
    public List<Audience> findAudiencesByBuildingId (int buildingId){
	 List<Audience> audiences = jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
	 Building building = buildingDao.findById(buildingId);
	for (Audience audience : audiences) {
	    audience.setBuilding(building);
	} 
	 return audiences;
    }
    
    
    
    
}
