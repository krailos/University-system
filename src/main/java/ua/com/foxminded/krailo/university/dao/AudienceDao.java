package ua.com.foxminded.krailo.university.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCE_BY_ID = "SELECT * FROM audiences  WHERE audience_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT * FROM audiences";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE audience_id = ?";
    private static final String SQL_UPDATE = "";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Audience> audienceRowMapper;
    private SimpleJdbcInsert simpleJdbcInsert;

    public AudienceDao(JdbcTemplate jdbcTemplate, RowMapper<Audience> audienceRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.audienceRowMapper = audienceRowMapper;
    }

    public Audience findById(int id) {
	Audience audience = jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_ID, new Object[] { id },
		audienceRowMapper);
	return audience;
    }

    public List<Audience> findByBuildingId(int buildingId) {
	List<Audience> audiences = jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
	return audiences;
    }

    public List<Audience> findAll() {
	return jdbcTemplate.query(SQL_SELECT_ALL_AUDIENCES, audienceRowMapper);
    }

    public void create(Audience audience) {
	setSimpleJdbcInsert();
	Map<String, Object> parameters = new HashMap<String, Object>(2);
	parameters.put("number", audience.getNumber());
	parameters.put("building_id", audience.getBuilding().getId());
	parameters.put("capacity", audience.getCapacity());
	parameters.put("description", audience.getDescription());
	Number audienceId = simpleJdbcInsert.executeAndReturnKey(parameters);
	audience.setId(audienceId.intValue());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private void setSimpleJdbcInsert() {
	simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("audiences")
		.usingGeneratedKeyColumns("audience_id");
    }

}
