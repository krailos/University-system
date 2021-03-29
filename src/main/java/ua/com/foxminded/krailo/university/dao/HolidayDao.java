package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Holiday;

@Component
public class HolidayDao {

    private static final Logger log = LoggerFactory.getLogger(HolidayDao.class);

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

    public Optional<Holiday> findById(int id) {
	log.debug("Find holiday by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_HOLIDAY_BY_ID, holidayRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Holiday with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find holiday by id=" + id, e);
	}
    }

    public List<Holiday> findAll() {
	log.debug("Find all holidays");
	try {
	    return jdbcTemplate.query(SQL_SELECT_HOLIDAYS, holidayRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all holidays", e);
	}
    }

    public void create(Holiday holiday) {
	log.debug("Create holiday={}", holiday);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_HOLIDAY, new String[] { "id" });
		ps.setString(1, holiday.getName());
		ps.setObject(2, holiday.getDate());
		return ps;
	    }, keyHolder);
	    holiday.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created holiday=%", holiday));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create holiday=%s" + holiday, e);
	}
	log.info("Created holiday={}", holiday);
    }

    public void update(Holiday holiday) {
	log.debug("Update holiday={}", holiday);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_HOLIDAY_BY_ID, holiday.getName(), holiday.getDate(),
		    holiday.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created, holiday=%", holiday));
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update department=" + holiday, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated holiday={}", holiday);
	} else {
	    log.debug("Not updated holiday={}", holiday);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete holiday by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_HOLIDAY_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete holiday by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted holiday by id={}", id);
	} else {
	    log.debug("Not deleted holiday by id={}", id);
	}
    }

    public Optional<Holiday> findByDate(LocalDate date) {
	log.debug("find holiday by date={}", date);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_HOLIDAY_BY_DATE, holidayRowMapper, date));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Holiday with date={} not found", date);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find holiday by date=" + date, e);
	}
    }

}
