package ua.com.foxminded.krailo.university.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Holiday;

@Component
public class HolidayRowMapper implements RowMapper<Holiday> {

    @Override
    public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException {
	Holiday holiday = new Holiday();
	holiday.setId(rs.getInt("id"));
	holiday.setName(rs.getString("name"));
	holiday.setDate(rs.getObject("date", LocalDate.class));
	return holiday;
    }

}
