package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Building;

@Component
public class BuildingRowMapper implements RowMapper<Building> {

    @Override
    public Building mapRow(ResultSet rs, int rowNum) throws SQLException {
	Building building = new Building();
	building.setId(rs.getInt("id"));
	building.setName(rs.getString("name"));
	building.setAddress(rs.getString("address"));
	return building;
    }

}
