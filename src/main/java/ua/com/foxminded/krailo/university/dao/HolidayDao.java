package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import ua.com.foxminded.krailo.university.model.Holiday;

public class HolidayDao {

    private static final String SQL_SELECT_HOLIDAYS = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays ORDER BY holiday_id";
    private static final String SQL_SELECT_HOLIDAY_BY_ID = "SELECT holiday_id, holiday_name, holiday_date  FROM holidays WHERE holiday_id = ?";
    private static final String SQL_INSERT_HOLIDAY_NAMED_PARAMETERS = "INSERT INTO holidays ( holiday_name, holiday_date) VALUES (:name, :date)";
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
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
	return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Holiday findById(int id) {
	return getJdbcTemplate().queryForObject(SQL_SELECT_HOLIDAY_BY_ID, holidayRowMapper, id);
    }

    public List<Holiday> findAll() {
	return getJdbcTemplate().query(SQL_SELECT_HOLIDAYS, holidayRowMapper);
    }

    public void addHoliday(Holiday holiday) {
	SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(holiday);
	getNamedParameterJdbcTemplate().update(SQL_INSERT_HOLIDAY_NAMED_PARAMETERS, namedParameters);
    }
    
    public void updateName (String name, int id) {
	getJdbcTemplate().update(SQL_UPDATE_HOLIDAY_NAME_BY_ID, name, id);
    }
    
    public void updateDate (LocalDate date , int id) {
 	getJdbcTemplate().update(SQL_UPDATE_HOLIDAY_DATE_BY_ID, Date.valueOf(date), id);
     }

    public void deleteById (int id) {
 	getJdbcTemplate().update(SQL_DELETE_HOLIDAY_BY_ID, id);
     }

}
