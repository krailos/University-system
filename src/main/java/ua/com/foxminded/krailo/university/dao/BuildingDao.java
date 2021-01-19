package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.mapper.BuildingRowMapper;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class BuildingDao {

    private static final String SQL_SELECT_BUILDINGS = "SELECT * FROM buildings ORDER BY id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT * FROM buildings WHERE id = ?";
    private static final String SQL_INSERT_BUILDING = "INSERT INTO buildings (name, address) VALUES (?, ?)";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;
     
    @Autowired
    private BuildingRowMapper buildingRowMapper;
    
    public List<Building> findAll() {
	return jdbcTemplate.query(SQL_SELECT_BUILDINGS, buildingRowMapper);
    }

    public Building findById(int id) {
	Building building = jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper);
	return building;
    }

    public void addBuilding(Building building) {
	jdbcTemplate.update(SQL_INSERT_BUILDING, building.getName(), building.getAddress());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BUILDING_BY_ID, id);
    }
    
    public Building findByIdForAudience(int id) {
  	Building building = jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper);
  	return building;
      }

}
