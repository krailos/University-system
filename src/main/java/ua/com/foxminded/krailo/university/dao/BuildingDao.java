package ua.com.foxminded.krailo.university.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class BuildingDao {

    private static final String SQL_SELECT_BUILDINGS = "SELECT building_id, building_name, building_address  FROM buildings ORDER BY building_id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT building_id, building_name, building_address  FROM buildings WHERE building_id = ?";
    private static final String SQL_INSERT_BUILDING = "INSERT INTO buildings (building_name, building_address) VALUES (?, ?)";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE building_id = ?";

    private RowMapper<Building> buildingRowMapper = (ResultSet rs, int rowNum) -> {
	Building building = new Building();
	building.setId(rs.getInt("building_id"));
	building.setName(rs.getString("building_name"));
	building.setAddress(rs.getString("building_address"));
	return building;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Building> findAll() {
	return jdbcTemplate.query(SQL_SELECT_BUILDINGS, buildingRowMapper);
    }

    public Building findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper);
    }

    public void addBuilding(Building building) {
	jdbcTemplate.update(SQL_INSERT_BUILDING, building.getName(), building.getAudiences());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BUILDING_BY_ID, id);
    }

}
