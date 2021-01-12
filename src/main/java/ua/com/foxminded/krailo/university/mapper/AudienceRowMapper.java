package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Audience;

@Component
public class AudienceRowMapper implements RowMapper<Audience> {
    
    @Override
    public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
	Audience audience = new Audience();
	audience.setId(rs.getInt("audience_id"));
	audience.setNumber(rs.getString("audience_number"));
	audience.setCapacity(rs.getInt("audience_capacity"));
	audience.setDescription(rs.getString("audience_description"));
	return audience;
    }

}
