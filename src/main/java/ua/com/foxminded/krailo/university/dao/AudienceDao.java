package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Audience> findAudiencesByBuildingId (int buildingId){
	 return jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, new AudienceRowMapper(), buildingId);
    }
    
    
    
    
}
