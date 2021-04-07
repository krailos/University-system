package ua.com.foxminded.krailo.university.dao;

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
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT * FROM audiences";
    private static final String SQL_SELECT_WITH_LIMIT = "SELECT * FROM audiences ORDER BY number LIMIT ? OFFSET ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE id = ?";
    private static final String SQL_INSERT_AUDIENCE = "INSERT INTO audiences (number, capacity, description) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE audiences SET number = ?, capacity = ?, description = ? where id = ?";
    private static final String SQL_AUDIENCE_COUNT = "SELECT COUNT (*) AS count FROM audiences";

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
	    throw new DaoException("Unable to get audience with id=" + id, e);
	}

    }

    public Optional<Audience> findByNumber(String number) {
	log.debug("find audiences by number={}", number);
	try {
	    return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_NUMBER, new Object[] { number },
		    audienceRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    log.debug("Audience with number={} not found", number);
	    return Optional.empty();
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to get audience with number=" + number, e);
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
		PreparedStatement ps = connection.prepareStatement(SQL_INSERT_AUDIENCE,  new String[]{"id"});
		ps.setString(1, audience.getNumber());
		ps.setInt(2, audience.getCapacity());
		ps.setString(3, audience.getDescription());
		return ps;
	    }, keyHolder);
	    audience.setId(keyHolder.getKey().intValue());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Audiences not created, audience: " + audience, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to create audience: " + audience, e);
	}
	log.info("audience created={}", audience);
    }

    public void update(Audience audience) {
	log.debug("Updating audience={}", audience);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, audience.getNumber(), audience.getCapacity(),
		    audience.getDescription(), audience.getId());
	} catch (DataIntegrityViolationException e) {
	    throw new DaoConstraintViolationException("Audience not updated audience" + audience, e);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to update audience=" + audience, e);
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

    public List<Audience> findWithLimit(int limit, int offset) {
	log.debug("find audiences by page");
	try {
	    return jdbcTemplate.query(SQL_SELECT_WITH_LIMIT, audienceRowMapper, limit, offset);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find audiences by page", e);
	}	
    }
    
    public int findCount() {
	log.debug("find audiences count");
	try {
	return jdbcTemplate.queryForObject(SQL_AUDIENCE_COUNT, Integer.class);
	} catch (DataAccessException e) {
	    throw new DaoException("Unable to find audiences count", e);
	}	
    }

}
