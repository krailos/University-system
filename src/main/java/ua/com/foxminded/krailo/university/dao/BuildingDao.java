package ua.com.foxminded.krailo.university.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.mapper.BuildingRowMapper;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class BuildingDao {

    private static final String SQL_SELECT_BUILDINGS = "SELECT * FROM buildings ORDER BY id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT * FROM buildings WHERE id = ?";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private BuildingRowMapper buildingRowMapper;
    private SimpleJdbcInsert simpleJdbcInsert;

    public BuildingDao(JdbcTemplate jdbcTemplate, BuildingRowMapper buildingRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.buildingRowMapper = buildingRowMapper;
    }

    public List<Building> findAll() {
	return jdbcTemplate.query(SQL_SELECT_BUILDINGS, buildingRowMapper);
    }

    public Building findById(int id) {
	Building building = jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id },
		buildingRowMapper);
	return building;
    }

    public void create(Building building) {
	setSimpleJdbcInsert();
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("name", building.getName());
	parameters.put("address", building.getAddress());
	Number buildingId = simpleJdbcInsert.executeAndReturnKey(parameters);
	building.setId(buildingId.intValue());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BUILDING_BY_ID, id);
    }

    public Building findByIdForAudience(int id) {
	Building building = jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id },
		buildingRowMapper);
	return building;
    }

    private void setSimpleJdbcInsert() {
	simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("buildings")
		.usingGeneratedKeyColumns("id");
    }

}
