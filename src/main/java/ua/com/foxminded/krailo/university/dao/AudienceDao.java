package ua.com.foxminded.krailo.university.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.mapper.AudienceRowMapper;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCE_BY_ID = "SELECT audience_id, audience_number, audience_capacity, audience_description  FROM audiences  WHERE audience_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT audience_id, audience_number, audience_capacity, audience_description FROM audiences";
    private static final String SQL_INSERT_AUDIENCE = "INSERT INTO audiences (audience_number, building_id, audience_capacity, audience_description) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE audience_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AudienceRowMapper audienceRowMapper;

    @Autowired
    private BuildingDao buildingDao;

    public Audience findById(int id) {
	Audience audience =  jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_ID, new Object[] { id }, audienceRowMapper);
	audience.setBuilding(buildingDao.findById(id));
	return audience;
    }

    public List<Audience> findAudiencesByBuildingId(int buildingId) {
	List<Audience> audiences = jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
	Building building = buildingDao.findById(buildingId);
	for (Audience audience : audiences) {
	    audience.setBuilding(building);
	}
	return audiences;
    }

    public List<Audience> findAllAudiences() {
	return jdbcTemplate.query(SQL_SELECT_ALL_AUDIENCES, audienceRowMapper);
    }

    public void addAudience(Audience audience) {
	jdbcTemplate.update(SQL_INSERT_AUDIENCE, audience.getNumber(), audience.getBuilding().getId(),
		audience.getCapacity(), audience.getDescription());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

}
