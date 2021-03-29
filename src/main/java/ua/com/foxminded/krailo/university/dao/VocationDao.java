package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Year;
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
import ua.com.foxminded.krailo.university.model.Vocation;

@Component
public class VocationDao {

    private static final Logger log = LoggerFactory.getLogger(VocationDao.class);

    private static final String SQL_SELECT_ALL = "SELECT * FROM vocations ORDER BY id";
    private static final String SQL_SELECT_BY_TEACHER_ID = "SELECT * FROM vocations where teacher_id = ?";
    private static final String SQL_SELECT_BY_TEACHER_ID_AND_YEAR = "SELECT * FROM vocations where teacher_id = ? AND EXTRACT(YEAR FROM start_date) = ?";
    private static final String SQL_SELECT_BY_TEACHER_ID_AND_DATE = "SELECT * FROM vocations where teacher_id = ? AND ? between start_date AND end_date ";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM vocations WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE vocations SET kind = ?, applying_date = ?, start_date = ?, end_date = ?, teacher_id = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM vocations WHERE id = ?";
    private static final String SQL_INSERT_VOCATION = "INSERT INTO vocations (kind, applying_date, start_date, end_date, teacher_id) VALUES (?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Vocation> vocationRowMapper;

    public VocationDao(JdbcTemplate jdbcTemplate, RowMapper<Vocation> vocationRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.vocationRowMapper = vocationRowMapper;
    }

    public Optional<Vocation> findById(int id) {
	log.debug("Find vocation by id={}", id);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, vocationRowMapper, id));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("vocation with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find vocation by id=" + id, e);
	}
    }

    public List<Vocation> findAll() {
	log.debug("Find all vocations");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL, vocationRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all vocations", e);
	}
    }

    public List<Vocation> findByTeacherId(int teacherId) {
	log.debug("Find vocations by teacherId={}", teacherId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_ID, vocationRowMapper, teacherId);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find vocations by teacherId=" + teacherId, e);
	}
    }

    public void create(Vocation vocation) {
	log.debug("Create vocation={}", vocation);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_VOCATION, new String[] { "id" });
		ps.setString(1, vocation.getKind().toString());
		ps.setDate(2, Date.valueOf(vocation.getApplyingDate()));
		ps.setDate(3, Date.valueOf(vocation.getStart()));
		ps.setDate(4, Date.valueOf(vocation.getEnd()));
		ps.setInt(5, vocation.getTeacher().getId());
		return ps;
	    }, keyHolder);
	    vocation.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not created vocation=" + vocation, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create vocation=" + vocation, e);
	}
	log.info("Created vocation={}", vocation);
    }

    public void update(Vocation vocation) {
	log.debug("Update vocation={}", vocation);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, vocation.getKind().toString(),
		    Date.valueOf(vocation.getApplyingDate()), Date.valueOf(vocation.getStart()),
		    Date.valueOf(vocation.getEnd()), vocation.getTeacher().getId(), vocation.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Not updated, vocation=" + vocation, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update vocation=" + vocation, e);
	}
	if (rowsAffected > 0) {
	    log.info("Updated vocation={}", vocation);
	} else {
	    log.debug("Not updated vocation={}", vocation);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete vocation by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete vocation by id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("Deleted vocation  by id={}", id);
	} else {
	    log.debug("Not deleted vocation by id={}", id);
	}
    }

    public List<Vocation> findByTeacherIdAndYear(int teacherId, Year year) {
	log.debug("Find vocations by teacherId={} and year={}", teacherId, year);
	try {
	    return jdbcTemplate.query(SQL_SELECT_BY_TEACHER_ID_AND_YEAR, vocationRowMapper, teacherId, year.getValue());
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find vocations by teacherId=%s and year=%s", teacherId, year), e);
	}
    }

    public Optional<Vocation> findByTeacherIdAndDate(int teacherId, LocalDate date) {
	log.debug("Find vocation by teacherId={} and date={}", teacherId, date);
	try {
	    return Optional.of(
		    jdbcTemplate.queryForObject(SQL_SELECT_BY_TEACHER_ID_AND_DATE, vocationRowMapper, teacherId, date));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("vocation with teacherId={} and date={} not found", teacherId, date);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find vocation by teacherId=%s and date=%s", teacherId, date), e);
	}
    }

}
