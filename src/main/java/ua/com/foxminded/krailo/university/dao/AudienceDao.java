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
import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDao {

    private static final Logger log = LoggerFactory.getLogger(AudienceDao.class);
    private static final String SQL_SELECT_AUDIENCE_BY_ID = "SELECT * FROM audiences  WHERE id = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER = "SELECT * FROM audiences  WHERE number = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID = "SELECT * FROM audiences  WHERE number = ? AND building_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT * FROM audiences";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE id = ?";
    private static final String SQL_INSERT_AUDIENCE = "INSERT INTO audiences (number, building_id, capacity, description) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE audiences SET number = ?, building_id = ?, capacity = ?, description = ? where id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Audience> audienceRowMapper;

    public AudienceDao(JdbcTemplate jdbcTemplate, RowMapper<Audience> audienceRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.audienceRowMapper = audienceRowMapper;
    }

    public Optional<Audience> findById(int id) {
	log.debug("find audience by id={}", id);
	try {
	    return Optional
		    .of(jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_ID, new Object[] { id }, audienceRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Audience with id={} not found", id);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    String msg = format("Unable to get audience with id=%s", id);
	    throw new DaoException(msg, e);
	}

    }

    public List<Audience> findByNumber(String number) {
	log.debug("find audiences by number={}", number);
	try {
	    return jdbcTemplate.query(SQL_SELECT_AUDIENCE_BY_NUMBER, new Object[] { number }, audienceRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find audiences with number=%s", number), e);
	}
    }

    public List<Audience> findByBuildingId(int buildingId) {
	log.debug("find audiences by building id={}", buildingId);
	try {
	    return jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to find audiences with building id=%s", buildingId), e);
	}
    }

    public List<Audience> findAll() {
	log.debug("find all audiences ");
	try {
	    return jdbcTemplate.query(SQL_SELECT_ALL_AUDIENCES, audienceRowMapper);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find all audiences", e);
	}
    }

    public void create(Audience audience) {
	log.debug("Creating audience={}", audience);
	try {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_AUDIENCE, new String[] { "id" });
		ps.setString(1, audience.getNumber());
		ps.setInt(2, audience.getBuilding().getId());
		ps.setInt(3, audience.getCapacity());
		ps.setString(4, audience.getDescription());
		return ps;
	    }, keyHolder);
	    audience.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Audiences not created", e);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to create audience=%s", audience), e);
	}
	log.info("audience created={}", audience);
    }

    public void update(Audience audience) {
	log.debug("Updating audience={}", audience);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, audience.getNumber(), audience.getBuilding().getId(),
		    audience.getCapacity(), audience.getDescription(), audience.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException(format("Audience not updated audience=%s", audience), e);
	} catch (DataAccessException e) {
	    throw new DaoException(format("Unable to update audience=%s", audience), e);
	}
	if (rowsAffected > 0) {
	    log.info("audience updated={}", audience);
	} else {
	    log.debug("audience not updated={}", audience);
	}
    }

    public void deleteById(int id) {
	log.debug("Delete audience by id={}", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to delete audience with id=" + id, e);
	}
	if (rowsAffected > 0) {
	    log.info("audience deleted with id={}", id);
	} else {
	    log.debug("audience not deleted, audience with id={} not exist", id);
	}
    }

    public Optional<Audience> findByNumberAndBuildingId(String number, int buildingId) throws DaoException {
	log.debug("find audience  by number={} and building id={}", number, buildingId);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID,
		    new Object[] { number, buildingId }, audienceRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("audiences not found by number={} and building id={}", number, buildingId);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException(
		    format("Unable to find audience by number %s and building id = %s", number, buildingId), e);
	}
    }

}
