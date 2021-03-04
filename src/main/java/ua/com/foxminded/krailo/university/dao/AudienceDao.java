package ua.com.foxminded.krailo.university.dao;

import static java.lang.String.format;

import java.sql.PreparedStatement;
import java.util.ArrayList;
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

import ua.com.foxminded.krailo.university.domain.Main;
import ua.com.foxminded.krailo.university.exception.ConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.QueryNotExecuteException;
import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDao {

    private static final String SQL_SELECT_AUDIENCE_BY_ID = "SELECT * FROM audiences  WHERE id = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER = "SELECT * FROM audiences  WHERE number = ?";
    private static final String SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID = "SELECT * FROM audiences  WHERE number = ? AND building_id = ?";
    private static final String SQL_SELECT_AUDIENCES_BY_BUILDING = "SELECT * FROM audiences WHERE building_id = ?";
    private static final String SQL_SELECT_ALL_AUDIENCES = "SELECT * FROM audiences";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM audiences WHERE id = ?";
    private static final String SQL_INSERT_AUDIENCE = "INSERT INTO audiences (number, building_id, capacity, description) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_BY_ID = "UPDATE audiences SET number = ?, building_id = ?, capacity = ?, description = ? where id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Audience> audienceRowMapper;

    public AudienceDao(JdbcTemplate jdbcTemplate, RowMapper<Audience> audienceRowMapper) {
	this.jdbcTemplate = jdbcTemplate;
	this.audienceRowMapper = audienceRowMapper;
    }

    public Audience findById(int id) {
	LOGGER.debug("find audience by id '{}'", id);
	Audience audience = new Audience();
	try {
	    audience = jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_ID, new Object[] { id }, audienceRowMapper);
	} catch (EmptyResultDataAccessException e) {
	    String msg = format("Audience with id %s not found", id);
	    LOGGER.debug(msg);
	    throw new EntityNotFoundException(msg);
	} catch (DataAccessException e) {
	    String msg = format("Unable to get audience with id %s", id);
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.debug("audience found '{}'", audience);
	return audience;
    }

    public List<Audience> findByNumber(String number) {
	LOGGER.debug("find audiences by number '{}'", number);
	List<Audience> audiences = new ArrayList<>();
	try {
	    audiences = jdbcTemplate.query(SQL_SELECT_AUDIENCE_BY_NUMBER, new Object[] { number }, audienceRowMapper);
	} catch (EmptyResultDataAccessException e) {
	    String msg = format("Audience with number %s not found", number);
	    LOGGER.debug(msg);
	    throw new EntityNotFoundException(msg);
	} catch (DataAccessException e) {
	    String msg = format("Unable to find audiences with number %s", number);
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.debug("audiences found '{}'", audiences);
	return audiences;
    }

    public List<Audience> findByBuildingId(int buildingId) {
	LOGGER.debug("find audiences by building id '{}'", buildingId);
	List<Audience> audiences = new ArrayList<>();
	try {
	    audiences = jdbcTemplate.query(SQL_SELECT_AUDIENCES_BY_BUILDING, audienceRowMapper, buildingId);
	} catch (DataAccessException e) {
	    String msg = format("Unable to find audiences with building id %s", buildingId);
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.debug("audiences found '{}'", audiences);
	return audiences;
    }

    public List<Audience> findAll() {
	LOGGER.debug("find all audiences ");
	List<Audience> audiences = new ArrayList<>();
	try {
	    audiences = jdbcTemplate.query(SQL_SELECT_ALL_AUDIENCES, audienceRowMapper);
	} catch (DataAccessException e) {
	    String msg = "Unable to find all audiences";
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.debug("all audiences found '{}'", audiences);
	return audiences;
    }

    public void create(Audience audience) {
	LOGGER.debug("Creating audience '{}'", audience);
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
	    String msg = "Audiences not created" + audience;
	    LOGGER.debug(msg);
	    throw new ConstraintViolationException(msg);
	} catch (DataAccessException e) {
	    String msg = "Unable to create audience" + audience;
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.info("audience created '{}'", audience);
    }

    public void update(Audience audience) {
	LOGGER.debug("Updating audience '{}'", audience);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_UPDATE_BY_ID, audience.getNumber(), audience.getBuilding().getId(),
		    audience.getCapacity(), audience.getDescription(), audience.getId());
	} catch (DataIntegrityViolationException e) {
	    String msg = "Audiences not updated" + audience;
	    LOGGER.debug(msg);
	    throw new ConstraintViolationException(msg);
	} catch (DataAccessException e) {
	    String msg = "Unable to update audience" + audience;
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	if (rowsAffected > 0) {
	    LOGGER.info("audience updated '{}'", audience);
	} else {
	    LOGGER.debug("audience not updated '{}'", audience);
	}
    }

    public void deleteById(int id) {
	LOGGER.debug("Delete audience  by id'{}'", id);
	int rowsAffected = 0;
	try {
	    rowsAffected = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
	} catch (DataAccessException e) {
	    String msg = format("Unable to delete audience with id %s", id);
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	if (rowsAffected > 0) {
	    LOGGER.info("audience deleted with id '{}'", id);
	} else {
	    LOGGER.debug("audience not deleted, audience id'{}' not exist", id);
	}
    }

    public Optional<Audience> findByNumberAndBuildingId(String number, int buildingId) {
	LOGGER.debug("find audience  by number '{}' and building id '{}'", number, buildingId);
	Optional<Audience> optional = Optional.empty();
	try {
	    optional = Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_AUDIENCE_BY_NUMBER_AND_BUILDING_ID,
		    new Object[] { number, buildingId }, audienceRowMapper));
	} catch (EmptyResultDataAccessException e) {
	    String msg = format("audiences not found by number %s and building id %s", number, buildingId);
	    LOGGER.debug(msg);
	    return optional;
	} catch (DataAccessException e) {
	    String msg = format("Unable to find audience by number %s and building id %s", number, buildingId);
	    LOGGER.error(msg, e);
	    throw new QueryNotExecuteException(msg, e);
	}
	LOGGER.debug("audience found '{}'", optional.get());
	return optional;
    }

}
