package ua.com.foxminded.krailo.university.dao;

import java.sql.ResultSet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ua.com.foxminded.krailo.university.model.Holiday;

public class HolidayDao {

    private static final String SQL_SELECT_HOLIDAYS = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays";

    private static final String SQL_SELECT_HOLIDAYS_BY_ID = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays WHERE holiday_id = ?";

    private RowMapper<Holiday> holidayRowMapper = (ResultSet rs, int rowNum) -> {
	    Holiday holiday = new Holiday();
	    holiday.setId(rs.getInt("holiday_id"));
	    holiday.setName(rs.getString("holiday_name"));
	    holiday.setDate((rs.getDate("holiday_date").toLocalDate()));
	    return holiday;
    };

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public Holiday findById(int id) {
	return getJdbcTemplate().queryForObject(SQL_SELECT_HOLIDAYS_BY_ID, new Object[] { id }, holidayRowMapper);
    }


}
