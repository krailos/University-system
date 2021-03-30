package ua.com.foxminded.krailo.university.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.model.Audience;

@Component
public class AudienceRowMapper implements RowMapper<Audience> {

    private BuildingDao buildingDao;

    public AudienceRowMapper(BuildingDao buildingDao) {
	this.buildingDao = buildingDao;
    }

    @Override
    public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
	Audience audience = new Audience();
	audience.setId(rs.getInt("id"));
	audience.setNumber(rs.getString("number"));
	buildingDao.findById(rs.getInt("building_id")).ifPresent(audience::setBuilding);
	audience.setCapacity(rs.getInt("capacity"));
	audience.setDescription(rs.getString("description"));
	return audience;
    }

}