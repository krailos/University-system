package ua.com.foxminded.krailo.university.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class BuildingDao {

    private static final String SQL_SELECT_BUILDINGS = "SELECT building_id, building_name, building_address  FROM buildings ORDER BY building_id";
    private static final String SQL_SELECT_BUILDING_BY_ID = "SELECT building_id, building_name, building_address  FROM buildings WHERE building_id = ?";
    private static final String SQL_INSERT_BUILDING_NAMED_PARAMETERS = "INSERT INTO buildings (building_name, building_address) VALUES (:name, :address)";
    private static final String SQL_DELETE_BUILDING_BY_ID = "DELETE FROM buildings WHERE building_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";

    private RowMapper<Building> buildingRowMapper = (ResultSet rs, int rowNum) -> {
	Building building = new Building();
	building.setId(rs.getInt("building_id"));
	building.setName(rs.getString("building_name"));
	building.setAddress(rs.getString("building_address"));
	List<Audience> audiences = getAudiencesByBuilding(building);
	building.setAudiences(audiences);
	return building;
    };

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSoureToJdbcTemplate(DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
	return namedParameterJdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Building> findAll() {
	return getJdbcTemplate().query(SQL_SELECT_BUILDINGS, buildingRowMapper);
    }

    public Building findById(int id) {
	return getJdbcTemplate().queryForObject(SQL_SELECT_BUILDING_BY_ID, new Object[] { id }, buildingRowMapper);
    }

    public void addBuilding(Building building) {
	SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(building);
	getNamedParameterJdbcTemplate().update(SQL_INSERT_BUILDING_NAMED_PARAMETERS, namedParameters);
    }

    public void deleteById(int id) {
	getJdbcTemplate().update(SQL_DELETE_BUILDING_BY_ID, id);
    }

    private List<Audience> getAudiencesByBuilding(Building building) {
	List<Audience> audiences = new ArrayList<>();
	List<Map<String, Object>> rows = getJdbcTemplate().queryForList(SQL_SELECT_AUDIENCES_BY_BUILDING,
		new Object[] { building.getId() });
	for (Map<String, Object> row : rows) {
	    Audience audience = new Audience();
	    audience.setId((int) row.get("audience_id"));
	    audience.setNumber((String) row.get("audience_number"));
	    audience.setCapacity((int) row.get("audience_capacity"));
	    audience.setDescription((String) row.get("audience_description"));
	    audience.setBuilding(building);
	    audiences.add(audience);
	}
	return audiences;
    }

}
