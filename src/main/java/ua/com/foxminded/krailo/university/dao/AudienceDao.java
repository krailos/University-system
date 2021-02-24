package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCE_BY_ID = "SELECT * FROM audiences  WHERE id = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER = "SELECT * FROM audiences  WHERE number = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID = "SELECT * FROM audiences  WHERE number = ? AND building_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT * FROM audiences";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE id = ?";
    private static final String SQL_INSERT_AUDIENCE = "INSERT INTO audiences (number, building_id, capacity, description) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE audiences SET number = ?, building_id = ?, capacity = ?, description = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Audience> audienceRowMapper;

    public AudienceDao(JdbcTemplate jdbcTemplate, RowMapper<Audience> audienceRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.audienceRowMapper = audienceRowMapper;
    }

    public Audience findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_ID, new Object[] { id }, audienceRowMapper);
    }

    public List<Audience> findByNumber(String name) {
	return jdbcTemplate.query(SQL_SELECT_AUDIENCE_BY_NUMBER, new Object[] { name }, audienceRowMapper);
    }

    public List<Audience> findByBuildingId(int buildingId) {
	return jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
    }

    public List<Audience> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL_AUDIENCES, audienceRowMapper);
    }

    public void create(Audience audience) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_AUDIENCE, new String[] { "id" });
	    ps.setString(1, audience.getNumber());
	    ps.setInt(2, audience.getBuilding().getId());
	    ps.setInt(3, audience.getCapacity());
	    ps.setString(4, audience.getDescription());
	    return ps;
	}, keyHolder);
	audience.setId(keyHolder.getKey().intValue());
    }

    public void update(Audience audience) {
	jdbcTemplate.update(SQL_UPDATE_BY_ID, audience.getNumber(), audience.getBuilding().getId(),
		audience.getCapacity(), audience.getDescription(), audience.getId());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public Audience findByNumberAndBuildingId(String number, int buildingId) {
	try {
	    return jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID,
		    new Object[] { number, buildingId }, audienceRowMapper);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	    // TODO: handle exception
	}
    }

}
