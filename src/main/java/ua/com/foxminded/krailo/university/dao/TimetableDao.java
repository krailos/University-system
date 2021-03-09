package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.PreparedStatement;
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
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.exception.DaoConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.model.Timetable;

@Repository
public class TimetableDao {

    private static final Logger log = LoggerFactory.getLogger(TimetableDao.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM timetables WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM timetables";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM timetables WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO timetables (name) VALUES (?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE timetables SET name = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Timetable> timetableRowMapper;

    public TimetableDao(JdbcTemplate jdbcTemplate, RowMapper<Timetable> timetableRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.timetableRowMapper = timetableRowMapper;
    }

    public void create(Timetable timetable) {
	log.debug("Create timetable={}", timetable);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] { "id" });
		ps.setString(1, timetable.getName());
		return ps;
	    }, keyHolder);
	    timetable.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not created timetable=%s", timetable));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create timetable=%s", timetable), e);
	}
	log.info("Created timetable={}", timetable);

    }

    public void update(Timetable timetable) {
	log.debug("Update timetable={}", timetable);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, timetable.getName(), timetable.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Not updated, timetable=%s", timetable));
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update timetable=%s", timetable));
	}
	if (rowsAffected > 0) {
	    log.info("Updated timetable={}", timetable);
	} else {
	    log.debug("Not updated timetable={}", timetable);
	}
    }

    public Optional<Timetable> findById(int id) {
	log.debug("Find timetable by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, timetableRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("timetable with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find timetable by id=%s", id), e);
	}
    }

    public List<Timetable> findAll() {
	log.debug("Find all timetables");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, timetableRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all timetables", e);

	}
    }

    public void deleteById(int id) {
	log.debug("Delete timetable by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to delete timetable by id=%s", id), e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted timetable  by id={}", id);
	} else {
	    log.debug("Not deleted timetable by id={}", id);
	}
    }

}
