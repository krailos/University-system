package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Component
public class UniversityOfficeRowMapper implements RowMapper<UniversityOffice> {

    @Override
    public UniversityOffice mapRow(ResultSet rs, int rowNum) throws SQLException {
	UniversityOffice universityOffice = new UniversityOffice();
	universityOffice.setId(rs.getInt("id"));
	universityOffice.setName(rs.getString("name"));
	universityOffice.setName(rs.getString("address"));
	return universityOffice;
    }

}
