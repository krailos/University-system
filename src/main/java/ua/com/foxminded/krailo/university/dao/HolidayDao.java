package ua.com.foxminded.krailo.university.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.model.Holiday;

@Component
public class HolidayDao {

    private static final String SQL_SELECT_HOLIDAYS = "SELECT * FROM holidays ORDER BY id";
    private static final String SQL_SELECT_HOLIDAY_BY_ID = "SELECT * FROM holidays WHERE id = ?";
    private static final String SQL_SELECT_HOLIDAY_BY_DATE = "SELECT * FROM holidays WHERE date = ?";
    private static final String SQL_UPDATE_HOLIDAY_BY_ID = "UPDATE holidays SET name = ?, date = ? WHERE id = ?";
    private static final String SQL_DELETE_HOLIDAY_BY_ID = "DELETE FROM holidays WHERE id = ?";
    private static final String SQL_INSERT_HOLIDAY = "INSERT INTO holidays (name, date) VALUES (?, ?)";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Holiday> holidayRowMapper;

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
	KeyHolder keyHolder = new GeneratedKeyHolder();
	jdbcTemplate.update(connection -> {
	    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_HOLIDAY, new String[] { "id" });
	    ps.setString(1, holiday.getName());
	    ps.setObject(2, holiday.getDate());
	    return ps;
	}, keyHolder);
	holiday.setId(keyHolder.getKey().intValue());
    }

    public void update(Holiday holiday) {
	jdbcTemplate.update(SQL_UPDATE_HOLIDAY_BY_ID, holiday.getName(), holiday.getDate(), holiday.getId());
    }

    public void deleteById(int id) {
	jdbcTemplate.update(SQL_DELETE_HOLIDAY_BY_ID, id);
    }

    public Holiday findByDate(LocalDate date) {
	try {
	    return jdbcTemplate.queryForObject(SQL_SELECT_HOLIDAY_BY_DATE, holidayRowMapper, date);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

}
