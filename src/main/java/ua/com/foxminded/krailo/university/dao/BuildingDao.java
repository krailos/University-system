package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.mapper.BuildingRowMapper;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class BuildingDao {

    private static final String SQL_SELECT_BUILDINGS = "SELECT * FROM buildings ORDER BY id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT * FROM buildings WHERE id = ?";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE id = ?";
    private static final String SQL_INSERT_BUILDING = "INSERT INTO buildings (name, address) VALUES (?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE buildings SET name = ?, address = ? WHERE id = ? ";

    private JdbcTemplate jdbcTemplate;
    private BuildingRowMapper buildingRowMapper;

    public BuildingDao(JdbcTemplate jdbcTemplate, BuildingRowMapper buildingRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.buildingRowMapper = buildingRowMapper;
    }

    public List<Building> findAll() {
	return jdbcTemplate.query(SQL_SELECT_BUILDINGS, buildingRowMapper);
    }

    public Building findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper);
    }

    public void create(Building building) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_BUILDING, new String[] { "id" });
	    ps.setString(1, building.getName());
	    ps.setString(2, building.getAddress());
	    return ps;
	}, keyHolder);
	building.setId(keyHolder.getKey().intValue());
    }
    
    public void update (Building building) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, building.getName(), building.getAddress(), building.getId());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BUILDING_BY_ID, id);
    }

}
