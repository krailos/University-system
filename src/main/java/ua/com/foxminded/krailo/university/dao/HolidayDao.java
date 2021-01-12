package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Holiday;

@Component
public class HolidayDao {

    private static final String SQL_SELECT_HOLIDAYS = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays ORDER BY holiday_id";
    private static final String SQL_SELECT_HOLIDAY_BY_ID = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays WHERE holiday_id = ?";
    private static final String SQL_INSERT_HOLIDAY = "INSERT INTO holidays ( holiday_name, holiday_date) VALUES (?, ?)";
    private static final String SQL_UPDATE_HOLIDAY_NAME_BY_ID = "UPDATE holidays SET holiday_name = ? WHERE holiday_id = ?";
    private static final String SQL_UPDATE_HOLIDAY_DATE_BY_ID = "UPDATE holidays SET holiday_date = ? WHERE holiday_id = ?";
    private static final String SQL_DELETE_HOLIDAY_BY_ID = "DELETE FROM holidays WHERE holiday_id = ?";
 
    private RowMapper<Holiday> holidayRowMapper = (ResultSet rs, int rowNum) -> {
	Holiday holiday = new Holiday();
	holiday.setId(rs.getInt("holiday_id"));
	holiday.setName(rs.getString("holiday_name"));
	holiday.setDate((rs.getDate("holiday_date").toLocalDate()));
	return holiday;
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Holiday findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_HOLIDAY_BY_ID, holidayRowMapper, id);
    }

    public List<Holiday> findAll() {
	return jdbcTemplate.query(SQL_SELECT_HOLIDAYS, holidayRowMapper);
    }

    public void addHoliday(Holiday holiday) {
	jdbcTemplate.update(SQL_INSERT_HOLIDAY, holiday.getName(), Date.valueOf(holiday.getDate()));
    }

    public void updateNameById(String name, int id) {
	jdbcTemplate.update(SQL_UPDATE_HOLIDAY_NAME_BY_ID, name, id);
    }

    public void updateDateById(LocalDate date, int id) {
	jdbcTemplate.update(SQL_UPDATE_HOLIDAY_DATE_BY_ID, Date.valueOf(date), id);
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_HOLIDAY_BY_ID, id);
    }

}
