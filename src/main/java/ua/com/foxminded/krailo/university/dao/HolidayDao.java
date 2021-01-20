package ua.com.foxminded.krailo.university.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Holiday;

@Component
public class HolidayDao {

    private static final String SQL_SELECT_HOLIDAYS = "SELECT * FROM holidays ORDER BY id";
    private static final String SQL_SELECT_HOLIDAY_BY_ID = "SELECT * FROM holidays WHERE id = ?";
    private static final String SQL_UPDATE_HOLIDAY_NAME_BY_ID = "UPDATE holidays SET name = ? WHERE id = ?";
    private static final String SQL_UPDATE_HOLIDAY_DATE_BY_ID = "UPDATE holidays SET date = ? WHERE id = ?";
    private static final String SQL_DELETE_HOLIDAY_BY_ID = "DELETE FROM holidays WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Holiday> holidayRowMapper;
    private SimpleJdbcInsert simpleJdbcInsert;

    public HolidayDao(JdbcTemplate jdbcTemplate, RowMapper<Holiday> holidayRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.holidayRowMapper = holidayRowMapper;
    }

    public Holiday findById(int id) {
	return jdbcTemplate.queryForObject(SQL_SELECT_HOLIDAY_BY_ID, holidayRowMapper, id);
    }

    public List<Holiday> findAll() {
	return jdbcTemplate.query(SQL_SELECT_HOLIDAYS, holidayRowMapper);
    }

    public void create(Holiday holiday) {
	setSimpleJdbcInsert();
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("name", holiday.getName());
	parameters.put("date", holiday.getDate());
	Number holidayId = simpleJdbcInsert.executeAndReturnKey(parameters);
	holiday.setId(holidayId.intValue());
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

    private void setSimpleJdbcInsert() {
	simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("buildings")
		.usingGeneratedKeyColumns("id");
    }
}
